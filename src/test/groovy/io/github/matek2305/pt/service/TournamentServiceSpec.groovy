package io.github.matek2305.pt.service

import io.github.matek2305.pt.auth.AuthenticationFacade
import io.github.matek2305.pt.domain.entity.Match
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

    private AuthenticationFacade authenticationFacadeMock
    private TournamentRepository tournamentRepositoryMock
    private MatchRepository matchRepositoryMock
    private TournamentService tournamentService

    void setup() {
        authenticationFacadeMock = Mock(AuthenticationFacade)
        tournamentRepositoryMock = Mock(TournamentRepository)
        matchRepositoryMock = Mock(MatchRepository)
        tournamentService = new TournamentService(authenticationFacadeMock, tournamentRepositoryMock, matchRepositoryMock)
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
            def ex = thrown(ResourceNotFoundException)
            ex.message.contains(tournamentId as String)
    }

    def "should throw ValidationFailedException when start date is before actual date"() {
        given:
            def tournamentId = 1
            def startDateBeforeActual = LocalDateTime.now().minusMinutes(10)
        and:
            tournamentRepositoryMock.findOptional(tournamentId) >> Optional.of(new Tournament())
        when:
            tournamentService.addTournamentMatch(tournamentId, 'homeTeam', 'awayTeam', startDateBeforeActual)
        then:
            thrown(ValidationFailedException)
    }

    def "should throw ValidationFailedException when startDate is less than 90 minutes from now"() {
        given:
            def tournamentId = 1
            def startDateLessThan90MinutesFromNow = LocalDateTime.now().plusMinutes(60)
        and:
            tournamentRepositoryMock.findOptional(tournamentId) >> Optional.of(new Tournament())
        when:
            tournamentService.addTournamentMatch(tournamentId, 'homeTeam', 'awayTeam', startDateLessThan90MinutesFromNow)
        then:
            thrown(ValidationFailedException)
    }

    def "should throw ValidationFailedException when trying to add match as no admin"() {
        given:
            def tournamentId = 1
        and:
            tournamentRepositoryMock.findOptional(tournamentId) >> Optional.of(new Tournament(admin: 'admin'))
            authenticationFacadeMock.getLoggedUsername() >> 'notadmin'
        when:
            tournamentService.addTournamentMatch(tournamentId, 'homeTeam', 'awayTeam', LocalDateTime.now().plusMinutes(120))
        then:
            thrown(ValidationFailedException)
    }

    def "should save match entity"() {
        given:
            def tournamentId = 1
            def homeTeamName = 'homeTeamName'
            def awayTeamName = 'awayTeamName'
            def startDate = LocalDateTime.now().plusMinutes(120)
        and:
            tournamentRepositoryMock.findOptional(tournamentId) >> Optional.of(new Tournament(id: tournamentId))
        when:
            Match match = tournamentService.addTournamentMatch(tournamentId, homeTeamName, awayTeamName, startDate)
        then:
            match.homeTeamName == homeTeamName
            match.awayTeamName == awayTeamName
            match.startDate == startDate
            match.status == Match.Status.PREDICTION_AVAILABLE
            match.tournament.id == tournamentId
        and:
            1 * matchRepositoryMock.save(_ as Match) >> { Match m -> m }
    }
}
