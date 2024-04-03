package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreBoardTest {
    private static final String A_HOME_TEAM = "AHomeTeam";
    private static final String AN_AWAY_TEAM = "AnAwayTeam";

    @Test
    public void shouldStartNewMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);

        //then
        assertEquals(A_HOME_TEAM, scoreBoard.getHomeTeam());
        assertEquals(AN_AWAY_TEAM, scoreBoard.getAwayTeam());
    }

    @Test
    public void shouldHaveZerosAsDefaultMatchScore() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);

        //then
        assertEquals(0, scoreBoard.getHomeScore());
        assertEquals(0, scoreBoard.getAwayScore());
    }

    @ParameterizedTest
    @MethodSource("incorrectTeamsNames")
    public void shouldThrowExceptionWhenTeamNameIsNullOrEmpty(String homeTeam, String awayTeam) {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //expect
        assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startMatch(homeTeam, awayTeam),
                String.format("Teams names should not be null or empty. Provided names: [%s, %s]", homeTeam, awayTeam)
        );
    }


    @Test
    public void shouldUpdateMatchScore() {
        //given
        var scoreBoard = new DefaultScoreBoard();
        var expectedHomeScore = 0;
        var expectedAwayScore = 1;

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        scoreBoard.updateScore(expectedHomeScore, expectedAwayScore);

        //then
        assertEquals(expectedHomeScore, scoreBoard.getHomeScore());
        assertEquals(expectedAwayScore, scoreBoard.getAwayScore());
    }

    @Test
    public void shouldThrowAnExceptionWhenUpdateMatchScoreWithoutActiveMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //expect
        assertThrows(
                NoActiveMatchException.class,
                () -> scoreBoard.updateScore(10, 10),
                "An active match was not found");
    }

    @ParameterizedTest
    @MethodSource("incorrectMatchScores")
    public void shouldThrowAnExceptionWhenUpdateMatchScoreWithNegativeValues(int homeScore, int awayScore) {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //expect
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.updateScore(homeScore, awayScore),
                String.format("Score values should not be negative. Provided values: [%s, %s]", homeScore, awayScore));
    }

    @Test
    public void shouldFinishActiveMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        scoreBoard.finishMatch();

        //then
        assertNull(scoreBoard.getHomeTeam());
        assertNull(scoreBoard.getAwayTeam());
    }

    @Test
    public void shouldReturnEmptyScoreWhenNoActiveMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        scoreBoard.finishMatch();

        //then
        assertEquals(Optional.empty(), scoreBoard.getHomeScore());
        assertEquals(Optional.empty(), scoreBoard.getAwayScore());
    }

    @Test
    public void shouldReturnEmptyTeamsWhenNoActiveMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //when
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        scoreBoard.finishMatch();

        //then
        assertEquals(Optional.empty(), scoreBoard.getHomeTeam());
        assertEquals(Optional.empty(), scoreBoard.getAwayTeam());
    }

    @Test
    public void shouldThrowExceptionWhenFinishWithoutActiveMatch() {
        //given
        var scoreBoard = new DefaultScoreBoard();

        //expect
        scoreBoard.startMatch(A_HOME_TEAM, AN_AWAY_TEAM);
        assertThrows(
                NoActiveMatchException.class,
                scoreBoard::finishMatch,
                "An active match was not found");
    }

    @Test
    public void shouldSortSummaryByTotalScore() {
        //given
        var scoreBoardA = new DefaultScoreBoard();
        var scoreBoardB = new DefaultScoreBoard();
        var summaryGenerator = new SummaryGenerator(scoreBoardA, scoreBoardB);
        var homeTeamA = "homeTeamA";
        var awayTeamA = "awayTeamA";
        var homeTeamB = "homeTeamB";
        var awayTeamB = "awayTeamB";

        //when
        scoreBoardA.startMatch(homeTeamA, awayTeamA);
        scoreBoardB.startMatch(homeTeamB, awayTeamB);
        scoreBoardA.updateScore(1, 0);

        //then
        var summary = summaryGenerator.generateReport();
        assertEquals("""
                1. homeTeamA 1 - awayTeamA 0
                2. homeTeamB 0 - awayTeamB 0""",
                summary);
    }

    @Test
    public void shouldSortMatchesWithSameScoreLIFO() {
        //given
        var scoreBoardA = new DefaultScoreBoard();
        var scoreBoardB = new DefaultScoreBoard();
        var scoreBoardC = new DefaultScoreBoard();
        var scoreBoardD = new DefaultScoreBoard();
        var summaryGenerator = new SummaryGenerator(scoreBoardA, scoreBoardB, scoreBoardC, scoreBoardD);
        var homeTeamA = "homeTeamA";
        var awayTeamA = "awayTeamA";
        var homeTeamB = "homeTeamB";
        var awayTeamB = "awayTeamB";
        var homeTeamC = "homeTeamC";
        var awayTeamC = "awayTeamC";
        var homeTeamD = "homeTeamD";
        var awayTeamD = "awayTeamD";

        //when
        scoreBoardA.startMatch(homeTeamA, awayTeamA);
        scoreBoardB.startMatch(homeTeamB, awayTeamB);
        scoreBoardC.startMatch(homeTeamC, awayTeamC);
        scoreBoardD.startMatch(homeTeamD, awayTeamD);

        scoreBoardA.updateScore(0, 5);
        scoreBoardB.updateScore(2, 3);
        scoreBoardC.updateScore(1, 4);
        scoreBoardD.updateScore(5, 0);

        //then
        var summary = summaryGenerator.generateSummary();
        assertEquals("""
                1. homeTeamD 5 - awayTeamD 0
                2. homeTeamC 1 - awayTeamC 4
                3. homeTeamB 2 - awayTeamB 3
                4. homeTeamA 0 - awayTeamA 5""",
                summary);
    }

    private static Stream<Arguments> incorrectTeamsNames() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(A_HOME_TEAM, null),
                Arguments.of(null, AN_AWAY_TEAM),
                Arguments.of("", AN_AWAY_TEAM),
                Arguments.of(" ", AN_AWAY_TEAM),
                Arguments.of(A_HOME_TEAM, ""),
                Arguments.of(A_HOME_TEAM, " "),
                Arguments.of("", ""),
                Arguments.of(" ", " ")
        );
    }

    private static Stream<Arguments> incorrectMatchScores() {
        return Stream.of(
                Arguments.of(1, -1),
                Arguments.of(-12, 1),
                Arguments.of(-100, -9999)
        );
    }
}