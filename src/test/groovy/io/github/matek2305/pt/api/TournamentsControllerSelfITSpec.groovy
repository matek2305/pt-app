package io.github.matek2305.pt.api

import info.solidsoft.mockito.java8.api.WithBDDMockito
import io.github.matek2305.pt.domain.entity.Tournament
import io.github.matek2305.pt.exception.ValidationFailedException
import io.github.matek2305.pt.service.TournamentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@WebIntegrationTest
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = [ApiConfiguration, ServiceMockConfiguration])
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

    def "should return bad request when trying to create tournament without name"() {
        expect:
            mockMvc.perform(post('/tournaments').content("{\"name\": \"  \", \"description\": \"desc\"}").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("validationFailed")))
                    .andExpect(content().string(containsString("Podaj nazwę turnieju")))
    }

    def "should return bad request when choosen name is already taken"() {
        given:
            def name = 'Test'
        and:
            given(tournamentService.createTournament(eq(name), anyString())).willThrow(ValidationFailedException)
        expect:
            mockMvc.perform(post('/tournaments').content("{\"name\": \"${name}\"}").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string(containsString("validationFailed")))
    }

    def "should return created tournament"() {
        given:
            given(tournamentService.createTournament(anyString(), anyString()))
                    .willReturn(new Tournament(id: 1, name: 'name', description: 'description'))
        expect:
            mockMvc.perform(post('/tournaments').content("{\"name\": \"name\", \"description\": \"desc\"}").contentType(MediaType.APPLICATION_JSON))
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
}
