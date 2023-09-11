import java.util.ArrayList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class GerenciadorAppOnibus {
    private ArrayList<Usuario> usuarios;
    private DatabaseManagerApp databaseManager = new DatabaseManagerApp();
    PreparedStatement preparedStatement;
    public void queryUsuarios() {

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * from pacientes"
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            {
                while (resultSet.next()) {
                    Registro registro = new Registro();
                    CartaoGeral cartaoGeral = new CartaoGeral();
                    registro.setNome(resultSet.getString("Login"));
                    registro.setSenha(resultSet.getString("Senha"));
                    registro.setNome(resultSet.getString("Nome"));
                    registro.setCep(resultSet.getString("Cep"));
                    registro.setCpf(resultSet.getString("Cpf"));
                    registro.setEndereco(resultSet.getString("Endereco"));
                    Usuario user = new Usuario(registro, cartaoGeral);
                    usuarios.add(user);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
