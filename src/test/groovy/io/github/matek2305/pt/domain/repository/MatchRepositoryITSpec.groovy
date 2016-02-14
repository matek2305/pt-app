package io.github.matek2305.pt.domain.repository

import io.github.matek2305.pt.domain.entity.Match
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
@RepositoryITSpec
class MatchRepositoryITSpec extends Specification {

    @Autowired
    private MatchRepository matchRepository

    def "should autowire repository"() {
        expect:
            matchRepository != null
    }

    def "should save match entity and generate id"() {
        given:
            final Match match = new Match(
                    homeTeamName: 'Poland',
                    awayTeamName: 'Germany',
                    homeTeamScore: 2,
                    awayTeamScore: 0,
                    startDate: LocalDateTime.now(),
                    status: Match.Status.FINISHED)
        when:
            final Match savedMatch = matchRepository.save(match)
        then:
            savedMatch != null
            savedMatch.id != null
    }
}
