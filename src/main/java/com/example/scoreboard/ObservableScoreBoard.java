package com.example.scoreboard;

/**
 * An extension of <code>{@link ScoreBoard}</code> interface.
 * This enables availability for <code>{@link ScoreBoardObserver}</code> to monitor a scoreboard
 */
public interface ObservableScoreBoard extends ScoreBoard {
    /**
     * Add observer to scoreboard.
     * @param observer An observer
     */
    void addObserver(ScoreBoardObserver observer);
    /**
     * Remove observer from scoreboard.
     * @param observer An observer
     */
    void removeObserver(ScoreBoardObserver observer);
}
