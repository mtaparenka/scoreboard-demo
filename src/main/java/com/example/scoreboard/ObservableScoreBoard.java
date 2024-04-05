package com.example.scoreboard;

public interface ObservableScoreBoard extends ScoreBoard {
    void addObserver(ScoreBoardObserver observer);
    void removeObserver(ScoreBoardObserver observer);
}
