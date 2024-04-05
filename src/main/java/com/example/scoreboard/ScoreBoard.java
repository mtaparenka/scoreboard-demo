package com.example.scoreboard;

import java.util.Optional;

public interface ScoreBoard {
    void startMatch(String homeTeam, String awayTeam);
    void updateScore(int homeScore, int awayScore);
    void finishMatch();
    Optional<Integer> getHomeScore();
    Optional<Integer> getAwayScore();
    Optional<String> getHomeTeam();
    Optional<String> getAwayTeam();
}
