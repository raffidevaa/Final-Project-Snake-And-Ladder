import javax.swing.*;
import java.sql.*;
public class Player {
    private String userName;
    private int position;
    private int score; // Tambahkan score

    public Player(String userName) {
        this.userName = userName;
        this.position = 0;
        this.score = 0; // Inisialisasi score
    }

    public String getUserName() {
        return userName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        this.score++;
    }

    public int rollDice() {
        return (int) (Math.random() * 6) + 1;
    }

//    public void saveToDatabase(Connection conn) throws SQLException {
//        String sql = "INSERT INTO users (username, score) VALUES (?, ?) ON DUPLICATE KEY UPDATE score = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, userName);
//            stmt.setInt(2, score);
//            stmt.setInt(3, score);
//            stmt.executeUpdate();
//        }
//    }
//
//    public static Player loadFromDatabase(Connection conn, String userName) throws SQLException {
//        String sql = "SELECT * FROM users WHERE username = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setString(1, userName);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                Player player = new Player(rs.getString("username"));
//                player.setScore(rs.getInt("score"));
//                return player;
//            }
//        }
//        return null;
//    }
}
