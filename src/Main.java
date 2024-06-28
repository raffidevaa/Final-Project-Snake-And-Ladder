/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : C
 * Group    : 10
 * Members  :
 * 1. 5026231086 - Kurnia Yufi Satrio Laksono
 * 2. 5026231140 - Aqilah Ummu Al Nawiswary
 * 3. 5026231104 - Raffi Deva Anargya
 * ------------------------------------------------------
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // Load Snake and Ladder icon image
        ImageIcon icon = new ImageIcon(Main.class.getResource("/snake_and_ladder_icon.png"));

        // Welcome message with icon
        JLabel label = new JLabel("Welcome to Snake and Ladder Game!", icon, SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));

        JOptionPane.showMessageDialog(null, label, "Snake and Ladder Game", JOptionPane.PLAIN_MESSAGE);

        // Create game instance
        SnakeAndLadder game = new SnakeAndLadder(100);

        // Get number of players using JOptionPane
        int numberOfPlayers = getNumberOfPlayers();

        // Add players to the game
        addPlayersToGame(game, numberOfPlayers);

        // Initiate game
        game.initiateGame();

        // Create and display GUI with players
        createAndShowGUI(game);

        // Start the game loop
        startGameLoop(game);
    }

    private static int getNumberOfPlayers() {
        int numberOfPlayers = 0;
        while (numberOfPlayers <= 0) {
            try {
                numberOfPlayers = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter number of players:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a positive integer.");
            }
        }
        return numberOfPlayers;
    }

    private static void addPlayersToGame(SnakeAndLadder game, int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            String playerName = JOptionPane.showInputDialog(null, "Enter name of player " + (i + 1) + ":");
            Player player = new Player(playerName);
            game.addPlayer(player);
        }
    }

    private static void createAndShowGUI(SnakeAndLadder game) {
        GameBoardGUI gameBoardGUI = new GameBoardGUI(game.getPlayers());
        game.setGameBoardGUI(gameBoardGUI);
        SwingUtilities.invokeLater(() -> gameBoardGUI.setVisible(true));
    }

    private static void startGameLoop(SnakeAndLadder game) {
        do {
            for (Player player : game.getPlayers()) {
                if (game.getStatus() == 2) {
                    break;
                }

                showTurnDialog(player);
                playTurn(game, player);

                // Check game status after each player's turn
                if (game.getStatus() == 2) {
                    break;
                }
            }
        } while (game.getStatus() != 2);

        // Display winner and increment score
        Player winner = game.getWinner();
        if (winner != null) {
            winner.incrementScore();
            game.getGameBoardGUI().updatePlayerScores();
            JOptionPane.showMessageDialog(null, "The Winner Is: " + winner.getUserName());
        }

        // Option to play again
        int playAgain = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        if (playAgain == JOptionPane.YES_OPTION) {
            for (Player player : game.getPlayers()) {
                player.setPosition(0);
            }
            game.initiateGame();
            game.setStatus(0);
            game.getGameBoardGUI().updatePlayerPositions();
            startGameLoop(game);
        } else {
            game.stopAudio(); // Stop the audio if not playing again
            System.exit(0);
        }
    }

    private static void showTurnDialog(Player player) {
        JOptionPane.showMessageDialog(null, "It's " + player.getUserName() + "'s turn, please press OK");
    }

    private static void playTurn(SnakeAndLadder game, Player player) {
        boolean continueRolling = true;

        do {
            int diceNumber = player.rollDice();

            if (!continueRolling) {
                break;
            }

            JOptionPane.showMessageDialog(null, "Dice number: " + diceNumber);

            if (game.getStatus() == 2) {
                break;
            }

            int currentPosition = player.getPosition();
            game.movePlayer(player, diceNumber);
            int newPosition = player.getPosition();

            game.getGameBoardGUI().updatePlayerPositions();

            if (player.getPosition() == game.getBoardSize()) {
                break;
            }

            if (diceNumber != 6) {
                continueRolling = false;
            }
        } while (true);
    }
}
