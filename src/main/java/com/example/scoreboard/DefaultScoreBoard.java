package com.example.scoreboard;

import com.example.exceptions.MatchAlreadyStartedException;
import com.example.exceptions.NoActiveMatchException;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public final class DefaultScoreBoard implements ScoreBoard {
    private static final String TEAMS_NAMES_INVALID_MESSAGE = "Teams names should not be null or empty. Provided names: [%s, %s]";
    private static final String NO_ACTIVE_MATCH_EXCEPTION_MESSAGE = "An active match was not found";

    private int homeScore;
    private int awayScore;
    private String homeTeam;
    private String awayTeam;
    private boolean isMatchActive;

    public DefaultScoreBoard() {
        setDefaultScore();
        isMatchActive = false;
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        if (!isMatchActive) {
            validateNames(homeTeam, awayTeam);

            this.homeTeam = homeTeam;
            this.awayTeam = awayTeam;
            isMatchActive = true;
        } else {
            throw new MatchAlreadyStartedException("Match already started");
        }
    }

    @Override
    public void updateScore(int homeScore, int awayScore) {
        validateScore(homeScore, awayScore);

        if (isMatchActive) {
            this.homeScore = homeScore;
            this.awayScore = awayScore;
        } else {
            throw new NoActiveMatchException(NO_ACTIVE_MATCH_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public void finishMatch() {
        if (isMatchActive) {
            homeTeam = null;
            awayTeam = null;
            setDefaultScore();
            isMatchActive = false;
        } else {
            throw new NoActiveMatchException(NO_ACTIVE_MATCH_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public Optional<Integer> getHomeScore() {
        return getIfMatchActive(homeScore);
    }

    @Override
    public Optional<Integer> getAwayScore() {
        return getIfMatchActive(awayScore);
    }

    @Override
    public Optional<String> getHomeTeam() {
        return getIfMatchActive(homeTeam);
    }

    @Override
    public Optional<String> getAwayTeam() {
        return getIfMatchActive(awayTeam);
    }

    private static void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException(
                    String.format(
                            "Score values should not be negative. Provided values: [%s, %s]", homeScore, awayScore));
        }
    }

    private static void validateNames(String homeTeam, String awayTeam) {
        if (StringUtils.isAnyBlank(homeTeam, awayTeam)) {
            throw new IllegalArgumentException(
                    String.format(TEAMS_NAMES_INVALID_MESSAGE, homeTeam, awayTeam));
        }
    }

    private <T> Optional<T> getIfMatchActive(T value) {
        return isMatchActive ? Optional.of(value) : Optional.empty();
    }

    private void setDefaultScore() {
        homeScore = 0;
        awayScore = 0;
    }
}
