package com.github.matek2305.pt.api

import com.github.matek2305.pt.domain.entity.Match
import com.github.matek2305.pt.domain.entity.Tournament
import com.github.matek2305.pt.exception.ValidationFailedException
import com.github.matek2305.pt.service.TournamentService
import info.solidsoft.mockito.java8.api.WithBDDMockito
import java.time.LocalDateTime
import java.time.Month
import javax.json.Json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup
/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@ControllerITSpec
class TournamentsControllerSelfITSpec extends Specification implements WithBDDMockito {

    @Autowired
    private WebApplicationContext webApplicationContext

    @Autowired
    private TournamentService tournamentService

    @Autowired
    private TournamentsController tournamentsController

    private MockMvc mockMvc

    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build()
    }

    def "should return bad request when trying to create tournament with blank name"() {
        given:
            def requestContent = Json.createObjectBuilder()
                    .add('name', ' ')
                    .add('description', 'desc')
                    .build()
                    .toString()
        expect:
            mockMvc.perform(post('/tournaments').content(requestContent).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath('$.validationFailed').exists())
    }

    def "should return bad request when choosen name is already taken"() {
        given:
            def name = 'Test'
            def requestContent = Json.createObjectBuilder()
                    .add('name', name)
                    .build()
                    .toString()
        and:
            given(tournamentService.createTournament(eq(name), anyString())).willThrow(ValidationFailedException)
        expect:
            mockMvc.perform(post('/tournaments').content(requestContent).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath('$.validationFailed').exists())
    }

    def "should return created tournament"() {
        given:
            def requestContent = Json.createObjectBuilder()
                    .add('name', 'name')
                    .add('description', 'desc')
                    .build()
                    .toString()
        and:
            given(tournamentService.createTournament(anyString(), anyString()))
                    .willReturn(new Tournament(id: 1, name: 'name', description: 'description'))
        expect:
            mockMvc.perform(post('/tournaments').content(requestContent).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentTypeCompatibleWith('application/hal+json'))
    }

    def "should return ok when getting tournament"() {
        given:
            def tournamentId = 1
        and:
            given(tournamentService.getTournament(eq(tournamentId))).willReturn(Optional.of(new Tournament(id: 1, name: 'name', description: 'description')))
        expect:
            mockMvc.perform(get("/tournaments/${tournamentId}"))
                    .andExpect(status().isOk())
    }

    def "should return not found when getting tournament that not exist"() {
        given:
            def tournamentId = 1
        and:
            given(tournamentService.getTournament(eq(tournamentId))).willReturn(Optional.empty())
        expect:
            mockMvc.perform(get("/tournaments/${tournamentId}"))
                    .andExpect(status().isNotFound())
    }

    def "should return CREATED when POST to /tournaments/{tournamenId}/matches"() {
        given:
            def tournamentId = 1
            def homeTeamName = "Home team name"
            def awayTeamName = "Away team name"
            def startDateString = "2016-03-09T20:45"
            def request = Json.createObjectBuilder()
                    .add("homeTeamName", homeTeamName)
                    .add("awayTeamName", awayTeamName)
                    .add("startDate", startDateString)
                    .build()
                    .toString()
        and:
            given(tournamentService.addTournamentMatch(eq(tournamentId), eq(homeTeamName), eq(awayTeamName), eq(LocalDateTime.of(2016, Month.MARCH, 9, 20, 45))))
                    .willReturn(matchEntityExample())
        expect:
            mockMvc.perform(post("/tournaments/${tournamentId}/matches").content(request).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath('$._links.self').exists())
                    .andExpect(jsonPath('$._links.tournament').exists())
    }

    private static Match matchEntityExample() {
        return new Match(
                id: 1,
                homeTeamName: 'homeTeamName',
                awayTeamName: 'awayTeamName',
                homeTeamScore: 0,
                awayTeamScore: 0,
                status: Match.Status.PREDICTION_AVAILABLE,
                startDate: LocalDateTime.now(),
                tournament: tournamentEntityExample()
        )
    }

    private static Tournament tournamentEntityExample() {
        return new Tournament(
                id: 1,
                name: 'tournament',
                description: 'desription',
                admin: 'admin'
        )
    }
}
