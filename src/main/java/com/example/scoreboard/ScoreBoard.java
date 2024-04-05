package com.example.scoreboard;

import java.util.Optional;

/**
 * A worldCup scoreboard interface
 */
public interface ScoreBoard {
    /**
     * Starts a new match on the scoreboard.
     * @param homeTeam home team name
     * @param awayTeam away team name
     * @throws com.example.exceptions.MatchAlreadyStartedException when a match is already started
     */
    void startMatch(String homeTeam, String awayTeam);

    /**
     * Updates score of current match on the scoreboard.
     * @param homeScore absolute home score value
     * @param awayScore absolute away score value
     * @throws com.example.exceptions.NoActiveMatchException if no active match on the scoreboard
     * @throws IllegalArgumentException if any provided score values are negative
     */
    void updateScore(int homeScore, int awayScore);

    /**
     * Finishes current active match. This removes the match from the scoreboard.
     * @throws com.example.exceptions.NoActiveMatchException if no active match on the scoreboard
     */
    void finishMatch();

    /**
     * @return home score. If no active match, returns empty <code>{@link Optional}</code>
     */
    Optional<Integer> getHomeScore();
    /**
     * @return away score. If no active match, returns empty <code>{@link Optional}</code>
     */
    Optional<Integer> getAwayScore();
    /**
     * @return home team name. If no active match, returns empty <code>{@link Optional}</code>
     */
    Optional<String> getHomeTeam();
    /**
     * @return away team name. If no active match, returns empty <code>{@link Optional}</code>
     */
    Optional<String> getAwayTeam();
}
