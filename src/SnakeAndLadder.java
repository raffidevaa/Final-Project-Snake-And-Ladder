import javax.swing.*;
//import java.sql.Connection;
//import java.sql.SQLException;
import java.util.ArrayList;

public class SnakeAndLadder {
//    private UserData userData;
    private ArrayList<Player> players;
    private ArrayList<Snake> snakes;
    private ArrayList<Ladder> ladders;
    private ArrayList<Integer> swapPositions;
    private int boardSize;
    private int status;
    private Player winner;

    private GameBoardGUI gameBoardGUI;
    private NewAudioz playAudioz;



    public SnakeAndLadder(int boardSize) {
        this.boardSize = boardSize;
        this.players = new ArrayList<>();
        this.snakes = new ArrayList<>();
        this.ladders = new ArrayList<>();
        this.swapPositions = new ArrayList<>();
        this.status = 0;
        this.winner = null;
        this.playAudioz = new NewAudioz();
        playAudioz.playAudio("src/Audio/song.wav", true); // Pastikan jalur relatif benar
//        this.userData = new UserData();
    }

    public void stopAudio() {
        playAudioz.stopAudio();
    }

    public void playEffectAudio(String filePath) {
        playAudioz.playAudio(filePath, false);
    }

    public void setGameBoardGUI(GameBoardGUI gameBoardGUI) {
        this.gameBoardGUI = gameBoardGUI;
    }

    public void initiateGame() {
        int[][] snakePositions = {{29, 9}, {38, 15}, {47, 5}, {53, 33}, {62, 37}, {86, 54}, {92, 70}, {97, 25}};
        int[][] ladderPositions = {{2, 23}, {8, 34}, {20, 77}, {32, 68}, {41, 79}, {74, 88}, {82, 100}, {85, 95}};
        int[] swapPositionsArray = {16, 24, 35, 71};

        addSnakes(snakePositions);
        addLadders(ladderPositions);
        addSwapPositions(swapPositionsArray);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addSnake(Snake snake) {
        snakes.add(snake);
    }

    public void addSnakes(int[][] positions) {
        for (int[] pos : positions) {
            Snake snake = new Snake(pos[0], pos[1]);
            snakes.add(snake);
        }
    }

    public void addLadder(Ladder ladder) {
        ladders.add(ladder);
    }

    public void addLadders(int[][] positions) {
        for (int[] pos : positions) {
            Ladder ladder = new Ladder(pos[0], pos[1]);
            ladders.add(ladder);
        }
    }

    public void addSwapPositions(int[] positions) {
        for (int pos : positions) {
            swapPositions.add(pos);
        }
    }

    public void movePlayer(Player player, int steps) {
        int currentPosition = player.getPosition();
        int newPosition = currentPosition + steps;

        if (newPosition > boardSize) {
            newPosition = boardSize - (newPosition - boardSize);
        }

        player.setPosition(newPosition);

        for (Snake snake : snakes) {
            if (snake.getFromPosition() == player.getPosition()) {
                playEffectAudio("src/Audio/cartoon-falling.wav"); // Pastikan jalur relatif benar
                player.setPosition(snake.getToPosition());
                JOptionPane.showMessageDialog(null, player.getUserName() + " got bitten by a snake! Moved to position " + player.getPosition());
                break;
            }
        }

        for (Ladder ladder : ladders) {
            if (ladder.getFromPosition() == player.getPosition()) {
                playEffectAudio("src/Audio/ladder.wav"); // Pastikan jalur relatif benar
                player.setPosition(ladder.getToPosition());
                JOptionPane.showMessageDialog(null, player.getUserName() + " found a ladder! Moved to position " + player.getPosition());
                break;
            }
        }

        if (swapPositions.contains(player.getPosition())) {
            playEffectAudio("src/Audio/akh.wav");
            showSwapDialog(player);
        }

        if (player.getPosition() == boardSize) {
            playEffectAudio("src/Audio/win.wav"); // Pastikan jalur relatif benar
            status = 2;
            winner = player;
            JOptionPane.showMessageDialog(null, "Congratulations! " + player.getUserName() + " wins the game!");
        }
    }

    private void showSwapDialog(Player player) {
        ArrayList<Player> otherPlayers = new ArrayList<>(players);
        otherPlayers.remove(player);

        String[] playerNames = new String[otherPlayers.size()];
        for (int i = 0; i < otherPlayers.size(); i++) {
            playerNames[i] = otherPlayers.get(i).getUserName();
        }

        int choice = JOptionPane.showOptionDialog(null,
                player.getUserName() + " landed on a swap position! Choose a player to swap positions with:",
                "Swap Position", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, playerNames, playerNames[0]);

        if (choice >= 0 && choice < otherPlayers.size()) {
            Player other = otherPlayers.get(choice);
            int temp = other.getPosition();
            other.setPosition(player.getPosition());
            player.setPosition(temp);

            JOptionPane.showMessageDialog(null, player.getUserName() + " swapped positions with " + other.getUserName());
        } else {
            System.out.println("Invalid choice, no swap performed.");
        }
    }

//    public void savePlayerScore(Player player) {
//        try (Connection conn = userData.getConnection()) {
//            player.saveToDatabase(conn);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Player loadPlayerData(String userName) {
//        try (Connection conn = userData.getConnection()) {
//            return Player.loadFromDatabase(conn, userName);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public GameBoardGUI getGameBoardGUI() {
        return gameBoardGUI;
    }

    public Player getWinner() {
        return winner;
    }
}
