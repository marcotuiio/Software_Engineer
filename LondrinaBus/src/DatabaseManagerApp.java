import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Classe que permite conexão ao banco de dados de forma génerica, limpo e simples de ser utilizado
// em qualquer outro lugar do código de forma mais bem otimizada
public class DatabaseManagerApp {
    private static final String DB_URL = "";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private Connection connection;

    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
