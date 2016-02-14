package io.github.matek2305.pt.service

import io.github.matek2305.pt.domain.entity.Tournament
import io.github.matek2305.pt.domain.repository.TournamentRepository
import io.github.matek2305.pt.exception.ValidationFailedException
import spock.lang.Specification

/**
 * @author Mateusz Urbański <matek2305@gmail.com>
 */
class TournamentServiceSpec extends Specification {

    private TournamentRepository tournamentRepositoryMock
    private TournamentService tournamentService

    void setup() {
        tournamentRepositoryMock = Mock(TournamentRepository)
        tournamentService = new TournamentService(tournamentRepositoryMock)
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
}
