package bd;

import java.sql.*;
import java.util.Scanner;

public class Crud {
    static final String DB_URL = "jdbc:mysql://localhost:3306/crud";
    static final String USER = "root";
    static final String PASS = "151223";
    static Connection conn = null;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Conectado ao banco de dados!");
            boolean running = true;
            while (running) {
                System.out.println("1. Inserir | 2. Consultar | 3. Atualizar | 4. Deletar | 5. Sair");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> insert();
                    case 2 -> read();
                    case 3 -> update();
                    case 4 -> delete();
                    case 5 -> running = false;
                    default -> System.out.println("Opção inválida!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    static void insert() throws SQLException {
        System.out.println("Nome: ");
        String nome = scanner.next();
        String sql = "INSERT INTO pessoa (nome) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Inserido com sucesso!");
        }
    }

    static void read() throws SQLException {
        String sql = "SELECT * FROM pessoa";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nome: " + rs.getString("nome"));
            }
        }
    }

    static void update() throws SQLException {
        System.out.println("ID para atualizar: ");
        int id = scanner.nextInt();
        System.out.println("Novo nome: ");
        String nome = scanner.next();
        String sql = "UPDATE pessoa SET nome = ? WHERE id= ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Atualizado com sucesso!");
        }
    }

    static void delete() throws SQLException {
        System.out.println("ID para deletar: ");
        int id = scanner.nextInt();
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Deletado com sucesso!");
        }
    }
}
