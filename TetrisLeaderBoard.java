/*
 Name: Adetola Aladekoba
 This is the Leaderboard class. It creates the leaderboard
 to display high scores for players.
 26/04/2024
*/

import javax.swing.*;
import java.io.*;
import java.util.*;

public class TetrisLeaderBoard {

    private TetrisGame game;
    private int score;
    private int[] highScores = new int[10];
    private String[] playerNames = new String[10];
    private String scoresFile = "scores.dat";

    public TetrisLeaderBoard(TetrisGame game) {
        this.game = game;
        score = game.getScore();
        loadHighScores();
        checkAndUpdateScores();
    }

    public void checkAndUpdateScores() {
        if (game.getState() == 0 && score > highScores[highScores.length - 1]) {
            String playerName = JOptionPane.showInputDialog(
                                           "Game Over! Enter your name:");
            updateHighScores(playerName, score);
            writeScoresToFile();
        }
    }

    private void updateHighScores(String playerName, int newScore) {
        int indexToInsert = -1;
        for (int index = 0; index < highScores.length; index++) {
            if (newScore > highScores[index]) {
                indexToInsert = index;
                break;
            }
        }
        if (indexToInsert >= 0) {
            System.arraycopy(highScores, indexToInsert, highScores,
                    indexToInsert + 1, highScores.length - indexToInsert - 1);
            System.arraycopy(playerNames,indexToInsert,playerNames,
                    indexToInsert + 1, playerNames.length - indexToInsert - 1);
            highScores[indexToInsert] = newScore;
            playerNames[indexToInsert] = playerName;
        }
    }

    private void writeScoresToFile() {
        try (FileWriter writer = new FileWriter(scoresFile,false)){
            for (int index = 0; index < highScores.length; index++) {
                writer.write("Player: " + playerNames[index] + "  Score: " +
                        highScores[index] + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing scores to file: "+e.getMessage());
        }
    }

    private void loadHighScores() {
        try (Scanner scanner = new Scanner(new File(scoresFile))) {
            int index = 0;
            while (scanner.hasNextLine() && index < highScores.length) {
                String line = scanner.nextLine();
                String[] parts = line.split("Score: ");
                if (parts.length == 2) {
                    playerNames[index] = parts[0].substring("Player: "
                                                                .length());
                    highScores[index] = Integer.parseInt(parts[1]);
                    index++;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading high scores: " + e.getMessage());
        }
    }

    public String scoreBoard() {
        StringBuilder sb = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(scoresFile))) {
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading scores file: " + e.getMessage());
        }
        return sb.toString();
    }

    

    public int[] getHighScores() {
        return highScores;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }

    public String getScoresFile() {
        return scoresFile;
    }

}
