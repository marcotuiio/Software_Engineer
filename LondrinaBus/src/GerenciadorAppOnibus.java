import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class GerenciadorAppOnibus {
    private double valorCredito; //fazer getters e setters, mais vale um na mão do que dois voando.
    private ArrayList<CartaoGeral> cartoes = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private DatabaseManagerApp databaseManager = new DatabaseManagerApp();
    PreparedStatement preparedStatement;
    public void queryUsuario() {

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * from usuario"
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            {
                while (resultSet.next()) {
                    Usuario user = new Usuario();
                    user.setCpf(resultSet.getString("cpf"));
                    user.setLogin(resultSet.getString("login"));
                    user.setSenha(resultSet.getString("senha"));
                    user.setNome(resultSet.getString("nome"));
                    user.setCep(resultSet.getString("cep"));
                    user.setEndereco(resultSet.getString("endereco"));
                    user.setNumUnicoCartao(resultSet.getInt("numUnicoCartao"));
                    usuarios.add(user);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Usuario u : usuarios)
            System.out.printf("Usuário com dados: %s %s %d\n", u.getCpf(), u.getNome(), u.getNumUnicoCartao());
    }

    public void queryCartaoGeral() {

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * from cartaogeral"
            );
            ResultSet resultSet = preparedStatement.executeQuery();

            {
                while (resultSet.next()) {
                    CartaoGeral cartaoGeral = new CartaoGeral();
                    cartaoGeral.setNumUnico(resultSet.getInt("numUnico"));
                    cartaoGeral.setCodigoNFC(resultSet.getString("codigoNFC"));
                    cartaoGeral.setBeneficio(resultSet.getBoolean("isEstudante"));
                    cartoes.add(cartaoGeral);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        for  (CartaoGeral cg : cartoes)
            System.out.println("CartãoGeral id: " + cg.numUnico);
    }

    public void setValorCredito(boolean beneficio) {
        if (beneficio) {
            this.valorCredito = 2.4;
        } else {
            this.valorCredito = 4.8;
        }
    }

    public double getValorCredito() { return this.valorCredito; }
}
