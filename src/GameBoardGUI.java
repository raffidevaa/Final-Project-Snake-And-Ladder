import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameBoardGUI extends JFrame {

    private JPanel gamePanel;
    private ArrayList<Player> players;
    private final int[] snakes = {29, 38, 47, 53, 62, 86, 92, 97};
    private final int[] ladders = {2, 8, 20, 32, 41, 74, 82, 85};
    private final int[] swapPositionsArray = {16, 24, 35, 71}; // Initialize swap positions

    public GameBoardGUI(ArrayList<Player> players) {
        this.players = players;
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Snake and Ladder Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(10, 10));

        createBoardSquares();

        add(gamePanel, BorderLayout.CENTER);

        // Tambahkan panel untuk skor pemain
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new GridLayout(1, players.size()));
        for (Player player : players) {
            JLabel scoreLabel = new JLabel(player.getUserName() + ": " + player.getScore(), SwingConstants.CENTER);
            scoreLabel.setName(player.getUserName()); // Set name for easy updating
            scorePanel.add(scoreLabel);
        }
        add(scorePanel, BorderLayout.NORTH);
    }

    private void createBoardSquares() {
        for (int i = 100; i >= 1; i--) {
            JPanel square = new JPanel();
            square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            square.setPreferredSize(new Dimension(50, 50));
            square.setLayout(new BorderLayout());

            JLabel label = new JLabel(String.valueOf(i), SwingConstants.CENTER);

            if (isSnake(i)) {
                label.setForeground(Color.RED);
            } else if (isLadder(i)) {
                label.setForeground(Color.GREEN);
            } else if (isSwapPosition(i)) {
                label.setForeground(new Color(180, 140, 210)); // Light purple color for swap positions
            }

            square.add(label, BorderLayout.CENTER);
            addPlayerNamesToSquare(square, i);

            gamePanel.add(square);
        }
    }

    private boolean isSwapPosition(int position) {
        for (int swapPos : swapPositionsArray) {
            if (position == swapPos) {
                return true;
            }
        }
        return false;
    }

    private void addPlayerNamesToSquare(JPanel square, int position) {
        ArrayList<Player> playersOnSquare = new ArrayList<>();
        for (Player player : players) {
            if (player.getPosition() == position) {
                playersOnSquare.add(player);
            }
        }

        if (!playersOnSquare.isEmpty()) {
            JPanel playerNamesPanel = new JPanel();
            playerNamesPanel.setLayout(new GridLayout(playersOnSquare.size(), 1));
            for (Player player : playersOnSquare) {
                JLabel playerNameLabel = new JLabel(player.getUserName(), SwingConstants.CENTER);
                playerNameLabel.setForeground(Color.BLUE); // Set player name color to blue
                playerNamesPanel.add(playerNameLabel);
            }
            square.add(playerNamesPanel, BorderLayout.SOUTH);
        }
    }

    private boolean isSnake(int position) {
        for (int snake : snakes) {
            if (snake == position) {
                return true;
            }
        }
        return false;
    }

    private boolean isLadder(int position) {
        for (int ladder : ladders) {
            if (ladder == position) {
                return true;
            }
        }
        return false;
    }

    public void updatePlayerPositions() {
        gamePanel.removeAll();
        createBoardSquares();
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // Method untuk update skor pemain di panel skor
    public void updatePlayerScores() {
        Component[] components = ((JPanel) getContentPane().getComponent(1)).getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                for (Player player : players) {
                    if (label.getName().equals(player.getUserName())) {
                        label.setText(player.getUserName() + ": " + player.getScore());
                    }
                }
            }
        }
    }
}
