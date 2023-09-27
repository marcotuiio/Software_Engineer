package Controller;

import Model.Usuario;
import Model.CartaoGeral;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

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
                    usuarios.add(user);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Usuario u : usuarios)
            System.out.printf("Usuário com dados: %s %s %d\n", u.getCpf(), u.getNome());
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
                    cartaoGeral.setBeneficio(resultSet.getBoolean("beneficio"));
                    cartaoGeral.setCpfUser(resultSet.getString("cpfUser"));
                    cartoes.add(cartaoGeral);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        for  (CartaoGeral cg : cartoes)
            System.out.println("CartãoGeral id: " + cg.getNumUnico() + " usuario " + cg.getCpfUser());
    }

    public void setValorCredito(boolean beneficio) {
        if (beneficio) {
            this.valorCredito = 2.4;
        } else {
            this.valorCredito = 4.8;
        }
    }

    public double getValorCredito() {
        return this.valorCredito;
    }

    private Usuario criarUsuario(Scanner sc) {
        System.out.println("\n CRIANDO NOVO USUÁRIO: ");
        System.out.println("Informe nome: ");
        String nome = sc.next();
        System.out.println("Informe login: ");
        String login = sc.next();
        System.out.println("Informe senha: ");
        String senha = sc.next();
        System.out.println("Informe CPF: ");
        String cpf = sc.next();
        System.out.println("Informe CEP: ");
        String cep = sc.next();
        System.out.println("Informe endereço: ");
        String endereco = sc.next();

        CartaoGeral cartaoGeral = new CartaoGeral();

        Usuario usuario = new Usuario(login, senha, nome, cep, cpf, endereco);

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sqlInsertUser = "INSERT INTO usuario (login, senha, nome, cep, cpf, endereco) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsertUser);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, senha);
            preparedStatement.setString(3, nome);
            preparedStatement.setString(4, cep);
            preparedStatement.setString(5, cpf);
            preparedStatement.setString(6, endereco);

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                return null;
            }

            String sqlInsertCartao = "INSERT INTO cartaogeral (codigoNFC, cpfUser) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsertCartao);

            preparedStatement.setString(1, cartaoGeral.getCodigoNFC());
            preparedStatement.setString(2, usuario.getCpf());

            linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas == 0) {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuario;
    }

    private Usuario logarUsuario(Scanner sc) {
        System.out.println("\n LOGANDO USUÁRIO: ");
        System.out.println("Informe CPF: ");
        String cpf = sc.next();

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sql = "SELECT * FROM usuario WHERE cpf = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cep = resultSet.getString("cep");
                String endereco = resultSet.getString("endereco");

                Usuario usuarioEncontrado = new Usuario(login, senha, nome, cep, cpf, endereco);

//                System.out.println("Usuário encontrado");
                return usuarioEncontrado;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private CartaoGeral obterCartao(Usuario usuario) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sql = "SELECT * FROM cartaogeral WHERE cpfUser = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, usuario.getCpf());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int numUnico = resultSet.getInt("numUnico");
                String codigoNFC = resultSet.getString("codigoNFC");
                boolean beneficio = resultSet.getBoolean("beneficio");
                int saldoCredito = resultSet.getInt("saldoCredito");

                CartaoGeral cartaoGeral = new CartaoGeral(numUnico, codigoNFC, saldoCredito, beneficio, usuario.getCpf());
                return cartaoGeral;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void makeMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Sistema LondriBus - Escolha sua opção: ");
        System.out.println("1 - Cadastrar novo usuário\n" +
                "2 - Logar usuário\n" +
                "3 - Comprar créditos\n" +
                "4 - Solicitar benefício\n" +
                "0 - SAIR\n");
        int op = sc.nextInt();

        while (op != 0) {
            Usuario usuario;
            switch (op) {
                case 1:
                    usuario = criarUsuario(sc);
                    if (usuario == null)
                        System.out.println("Falha ao inserir usuário. Tente novamente");
                    break;

                    case 2:
                    usuario = logarUsuario(sc);
                    if (usuario == null)
                        System.out.println("Nenhum usuário encontrado com o CPF informado. Tente novamente\n");
                    break;

                case 3:
                    obterCartao(sc);
                    break;

                case 4:
                    obterCartao(sc);
                    break;

                case 5:
                    obterCartao(sc);
                    break;

                default:
                    System.out.println("Opção indisponível. Tente novamente.");
                    break;
            }

            System.out.println("Sistema LondriBus - Escolha sua opção: ");
            System.out.println("1 - Cadastrar novo usuário\n" +
                    "2 - Logar usuário\n" +
                    "3 - Emitir cartão\n" +
                    "4 - Comprar créditos\n" +
                    "5 - Solicitar benefício\n" +
                    "0 - SAIR\n");
            op = sc.nextInt();
        }
    }

    public void printStatusCartao(CartaoGeral cg) {
        if (cg.getBeneficio()) {
            System.out.println("CARTAO ESTUDANTE");
        } else {
            System.out.println("CARTAO GERAL");
        }
    }

}
