package com.example.summary;

import com.example.scoreboard.Event;
import com.example.scoreboard.ScoreBoard;
import com.example.scoreboard.ScoreBoardObserver;

import java.util.ArrayList;
import java.util.List;

public final class StringSummaryGenerator implements SummaryGenerator<String>, ScoreBoardObserver {
    private final List<ScoreBoard> scoreBoards;

    public StringSummaryGenerator() {
        scoreBoards = new ArrayList<>();
    }

    @Override
    public void update(ScoreBoard scoreBoard, Event scoreBoardEvent) {
        switch (scoreBoardEvent) {
            case MATCH_STARTED -> scoreBoards.add(scoreBoard);
            case MATCH_STOPPED -> scoreBoards.remove(scoreBoard);
        }
    }

    @Override
    public String generateSummary() {
        var builder = new StringBuilder();
        var sortedScoreBoards = sort(scoreBoards);

        for (int i = 0; i < sortedScoreBoards.size(); i++) {
            var currentBoard = sortedScoreBoards.get(i);


            builder.append(String.format("%d. %s %d - %s %d",
                    i + 1,
                    currentBoard.getHomeTeam().orElseThrow(),
                    currentBoard.getHomeScore().orElseThrow(),
                    currentBoard.getAwayTeam().orElseThrow(),
                    currentBoard.getAwayScore().orElseThrow()));
            if (i != sortedScoreBoards.size() - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    private static List<ScoreBoard> sort(List<ScoreBoard> scoreBoards) {
        return scoreBoards.stream()
                .sorted(StringSummaryGenerator::compareBoards)
                .toList();
    }

    private static int compareBoards(ScoreBoard scoreBoardA, ScoreBoard scoreBoardB) {
        var totalScoreA = scoreBoardA.getHomeScore().orElseThrow() + scoreBoardA.getAwayScore().orElseThrow();
        var totalScoreB = scoreBoardB.getHomeScore().orElseThrow() + scoreBoardB.getAwayScore().orElseThrow();
        if (totalScoreA > totalScoreB) {
            return -1;
        } else if (totalScoreA < totalScoreB) {
            return 1;
        } else {
            return -1;
        }
    }
}
