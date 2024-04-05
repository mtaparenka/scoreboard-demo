package com.example.scoreboard;

public interface ScoreBoardObserver {
    /**
     * Notifies observer about changes in scoreboard.
     * @param scoreBoard scoreboard to track
     * @param scoreBoardEvent event, produced by scoreboard
     */
    void update(ScoreBoard scoreBoard, Event scoreBoardEvent);
}
