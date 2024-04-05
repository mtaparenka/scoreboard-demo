package com.example.scoreboard;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * An implementation of <code>{@link ObservableScoreBoard}</code> interface.
 * This implementation can be monitored by observers which allows, for example,
 * generate summary of all active matches.
 * <pre>{@code
 *     var scoreBoardA = new ReportableScoreBoard();
 *     scoreBoardA.addObserver(summaryGenerator);
 *     summaryGenerator.generateSummary();
 * }</pre>
 * The implementation is Thread-unsafe.
 **/
public final class ReportableScoreBoard implements ObservableScoreBoard {
    private final ScoreBoard defaultScoreBoard;
    private final Set<ScoreBoardObserver> observers;

    public ReportableScoreBoard() {
        this.defaultScoreBoard = new DefaultScoreBoard();
        this.observers = new HashSet<>();
    }

    @Override
    public void addObserver(ScoreBoardObserver observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(ScoreBoardObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        defaultScoreBoard.startMatch(homeTeam, awayTeam);
        observers.forEach(observer -> observer.update(this, Event.MATCH_STARTED));
    }

    @Override
    public void updateScore(int homeScore, int awayScore) {
        defaultScoreBoard.updateScore(homeScore, awayScore);
    }

    @Override
    public void finishMatch() {
        defaultScoreBoard.finishMatch();
        observers.forEach(observer -> observer.update(this, Event.MATCH_STOPPED));
    }

    @Override
    public Optional<Integer> getHomeScore() {
        return defaultScoreBoard.getHomeScore();
    }

    @Override
    public Optional<Integer> getAwayScore() {
        return defaultScoreBoard.getAwayScore();
    }

    @Override
    public Optional<String> getHomeTeam() {
        return defaultScoreBoard.getHomeTeam();
    }

    @Override
    public Optional<String> getAwayTeam() {
        return defaultScoreBoard.getAwayTeam();
    }
}
