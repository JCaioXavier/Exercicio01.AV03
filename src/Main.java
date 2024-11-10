import java.sql.*;
import java.util.Scanner;

public class Main {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "5432";
    private static Connection conn;

    public static void conectar() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }
    }

    public static void jogosL(String search) {
        String sql = "SELECT titulo, subtitulo FROM jogos.jogos WHERE titulo ILIKE ? OR subtitulo ILIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + search + "%");
            stmt.setString(2, "%" + search + "%");
            ResultSet rs = stmt.executeQuery();

            System.out.println("Jogos que contém a string '" + search + "':");
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo") + ", Subtítulo: " + rs.getString("subtitulo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar jogos: " + e.getMessage());
        }
    }

    public static void JogosO() {
        String sql = "SELECT titulo, subtitulo FROM jogos.jogos ORDER BY titulo ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Jogos em ordem crescente pelo título:");
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo") + ", Subtítulo: " + rs.getString("subtitulo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar jogos em ordem: " + e.getMessage());
        }
    }

    public static void jogosK() {
        String sql = "SELECT titulo, subtitulo FROM jogos.jogos WHERE titulo ILIKE '%k%' OR subtitulo ILIKE '%k%'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            System.out.println("Jogos que contém 'k' no título ou no subtítulo:");
            while (rs.next()) {
                System.out.println("Título: " + rs.getString("titulo") + ", Subtítulo: " + rs.getString("subtitulo"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar jogos com 'k': " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        conectar();

        System.out.print("Digite uma string para buscar jogos: ");
        String search = scanner.nextLine();

        jogosL(search);

        JogosO();

        jogosK();
    }
}