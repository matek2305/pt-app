package io.github.matek2305.pt.service

import io.github.matek2305.pt.domain.entity.Tournament
import io.github.matek2305.pt.domain.repository.MatchRepository
import io.github.matek2305.pt.domain.repository.TournamentRepository
import io.github.matek2305.pt.exception.ResourceNotFoundException
import io.github.matek2305.pt.exception.ValidationFailedException
import spock.lang.Specification

import java.time.LocalDateTime
/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
class TournamentServiceSpec extends Specification {

    private TournamentRepository tournamentRepositoryMock
    private MatchRepository matchRepository
    private TournamentService tournamentService

    void setup() {
        tournamentRepositoryMock = Mock(TournamentRepository)
        tournamentService = new TournamentService(tournamentRepositoryMock, matchRepository)
    }

    def "should throw ValidationFailedException when name is already taken"() {
        given:
            def name = 'name'
        and:
            tournamentRepositoryMock.findByName(name) >> Optional.of(new Tournament())
        when:
            tournamentService.createTournament(name, null)
        then:
            ValidationFailedException ex = thrown()
            ex.message.contains(name)
    }

    def "should create tournament"() {
        given:
            def name = 'name'
            def desc = 'description'
        and:
            tournamentRepositoryMock.findByName(name) >> Optional.empty()
            tournamentRepositoryMock.save(_ as Tournament) >> { Tournament t -> t }
        when:
            def tournament = tournamentService.createTournament(name, desc)
        then:
            tournament != null
            tournament.name == name
            tournament.description == desc
    }

    def "should throw ResourceNotFoundException when trying to create match resource with invalid tournament id"() {
        given:
            def tournamentId = 1
        and:
            tournamentRepositoryMock.findOptional(tournamentId) >> Optional.empty()
        when:
            tournamentService.addTournamentMatch(tournamentId, 'homeTeam', 'awayTeam', LocalDateTime.now())
        then:
            def ex = Mock(ResourceNotFoundException)
            ex.message.contains(tournamentId as String)

    }
}
