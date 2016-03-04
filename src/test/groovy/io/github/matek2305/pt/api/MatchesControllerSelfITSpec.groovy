package io.github.matek2305.pt.api

import info.solidsoft.mockito.java8.api.WithBDDMockito
import io.github.matek2305.pt.domain.entity.Match
import io.github.matek2305.pt.domain.entity.Tournament
import io.github.matek2305.pt.service.MatchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

/**
 * @author Mateusz Urbański <matek2305@gmail.com>.
 */
@ControllerITSpec
class MatchesControllerSelfITSpec extends Specification implements WithBDDMockito {

    @Autowired
    private WebApplicationContext webApplicationContext

    @Autowired
    private MatchService matchService

    @Autowired
    private MatchesController matchesController

    private MockMvc mockMvc

    void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build()
    }

    @Unroll
    def "should have predictions link when status is #status" (Match.Status status) {
        given:
            def matchId = 12
        and:
            given(matchService.getMatch(matchId))
                    .willReturn(Optional.of(new Match(id: matchId, status: status, tournament: new Tournament(id: 1))))
        expect:
            mockMvc.perform(get("/matches/${matchId}")).andExpect(jsonPath('$._links.predictions').exists())
        where:
            status << [Match.Status.PREDICTION_CLOSED, Match.Status.STARTED, Match.Status.FINISHED, Match.Status.CANCELED]
    }

    def "should not have predictions link when status is PREDICTIONS_AVAILABLE" () {
        given:
            def matchId = 12
        and:
            given(matchService.getMatch(matchId))
                    .willReturn(Optional.of(new Match(id: matchId, status: Match.Status.PREDICTION_AVAILABLE, tournament: new Tournament(id: 1))))
        expect:
            mockMvc.perform(get("/matches/${matchId}")).andExpect(jsonPath('$._links.predictions').doesNotExist())
    }

}
