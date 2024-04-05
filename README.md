# Demo WorldCup scoreboard library
Simple scoreboard demo library that shows all the ongoing matches and their scores.
## Table of contents
* [Features](#features)
* [Examples of use](#examples-of-use)
* [Setup](#setup)
## Features
- Start a new match
- Update score
- Finish match currently in progress
- Get a summary of matches in progress ordered by their total score
## Examples of use
Start a new match. This will start a new match on a scoreboard with initial score 0-0.
``` java
new DefaultScoreBoard().startMatch("England", "Brazil");
```
Update score. This will update score of active match
``` java
scoreboard.updateScore(0, 1);
```
Finish match. This will remove the match from scoreboard.
``` java
scoreboard.finishMatch();
```
Get a summary of matches. This will return a summary of all active matches, sorted by their total score.
If two matches have same score, then they will be returned ordered by the most recently started match in the scoreboard.
To do this, you will need to use a special extension's implementation of `ScoreBoard` interface, called `ObservableScoreBoard`.
It will allow you to subscribe different observers to your scoreboards, so that way you can process multiple scoreboards data
``` java
var summaryGenerator = new StringSummaryGenerator();
var scoreBoardA = new ReportableScoreBoard();
var scoreBoardB = new ReportableScoreBoard();

scoreBoardA.addObserver(summaryGenerator);
scoreBoardB.addObserver(summaryGenerator);

scoreBoardA.startMatch("Brazil", "England");
scoreBoardB.startMatch("Mexico", "Poland");

scoreBoardA.updateScore(0, 2);
scoreBoardA.updateScore(1, 1);

summaryGenerator.generateSummary();
/* the result
1. Mexico 1 - Poland 1
2. Brazil 0 - England 2
*/
```
## Setup
```
.\gradlew build
```
