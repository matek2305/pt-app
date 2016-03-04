package io.github.matek2305.pt.service

import io.github.matek2305.pt.domain.entity.Match
import io.github.matek2305.pt.domain.repository.MatchPredictionRepository
import io.github.matek2305.pt.domain.repository.MatchRepository
import io.github.matek2305.pt.exception.ForbiddenResourceException
import io.github.matek2305.pt.exception.ResourceNotFoundException
import org.springframework.data.domain.PageRequest
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Mateusz Urbański <matek2305@gmail.com>.
 */
class MatchPredictionServiceSpec extends Specification {

    private MatchPredictionService matchPredictionService

    private MatchPredictionRepository matchPredictionRepositoryMock
    private MatchRepository matchRepositoryMock

    void setup() {
        matchPredictionRepositoryMock = Mock(MatchPredictionRepository)
        matchRepositoryMock = Mock(MatchRepository)
        matchPredictionService = new MatchPredictionService(matchPredictionRepositoryMock, matchRepositoryMock)
    }

    def "should throw ResourceNotFoundException when trying to get prediction list for non existing match"() {
        given:
            def matchId = 1
        and:
            matchRepositoryMock.findOptional(matchId) >> Optional.empty()
        when:
            matchPredictionService.getPredictionPageForMatch(matchId, 0, 10)
        then:
            thrown(ResourceNotFoundException)

    }

    def "should throw ForbiddenResourceException when trying to get prediction list for match with PREDICTION_AVAILABLE status"() {
        given:
            def matchId = 1
        and:
            matchRepositoryMock.findOptional(matchId) >> Optional.of(new Match(id: matchId, status: Match.Status.PREDICTION_AVAILABLE))
        when:
            matchPredictionService.getPredictionPageForMatch(matchId, 0, 10)
        then:
            thrown(ForbiddenResourceException)
    }

    @Unroll
    def "should call repository when trying to get prediction list for match with #status status"(Match.Status status) {
        given:
            def matchId = 1
        and:
            matchRepositoryMock.findOptional(matchId) >> Optional.of(new Match(id: matchId, status: status))
        when:
            matchPredictionService.getPredictionPageForMatch(matchId, 0, 10)
        then:
            1 * matchPredictionRepositoryMock.findByMatchId(matchId, _ as PageRequest)
        where:
            status << [Match.Status.PREDICTION_CLOSED, Match.Status.CANCELED, Match.Status.STARTED, Match.Status.FINISHED]

    }
}
