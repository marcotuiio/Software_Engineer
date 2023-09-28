package Controller;

import Model.FormularioBeneficio;
import Model.Usuario;
import Model.CartaoGeral;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class GerenciadorAppOnibus {
    private double valorCredito = 4.8; //fazer getters e setters, mais vale um na mão do que dois voando.
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
            System.out.printf("Usuário com dados: %s %s\n", u.getCpf(), u.getNome());
    }

    public void queryCartaoGeral() {

        databaseManager.connect();
        Connection connection = databaseManager.getConnection();
        String cpfUser = "";
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
                    cartoes.add(cartaoGeral);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        for  (CartaoGeral cg : cartoes)
            System.out.println("CartãoGeral id: " + cg.getNumUnico());
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
        System.out.print("Informe nome: ");
        String nome = sc.nextLine();
        System.out.print("Informe login: ");
        String login = sc.next();
        System.out.print("Informe senha: ");
        String senha = sc.next();
        System.out.print("Informe CPF: ");
        String cpf = sc.next();
        System.out.print("Informe CEP: ");
        String cep = sc.next();
        System.out.print("Informe endereço: ");
        String endereco = sc.nextLine();

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
            CartaoGeral cartaoGeral = new CartaoGeral();
            usuario.setCartaoGeral(cartaoGeral);

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
        System.out.print("Informe CPF: ");
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

                CartaoGeral cartaoGeral = obterCartao(usuarioEncontrado.getCpf());

                usuarioEncontrado.setCartaoGeral(cartaoGeral);

                return usuarioEncontrado;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private CartaoGeral obterCartao(String cpf) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sql = "SELECT * FROM cartaogeral WHERE cpfUser = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, cpf);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int numUnico = resultSet.getInt("numUnico");
                String codigoNFC = resultSet.getString("codigoNFC");
                boolean beneficio = resultSet.getBoolean("beneficio");
                int saldoCredito = resultSet.getInt("saldoGeral");
                int saldoBeneficio = resultSet.getInt("saldoBeneficio");

                CartaoGeral cartaoGeral = new CartaoGeral(numUnico, codigoNFC, saldoCredito, saldoBeneficio, beneficio);
                return cartaoGeral;

            } else {
                return null;
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    private void compraCredito(Usuario usuario, Scanner sc) {
        if (usuario == null) {
            System.out.println("Você precisa estar logado para comprar créditos.");
            return;
        }
        System.out.print("Comprar crédito normal (1) ou com benefício (2): ");
        int tipo = sc.nextInt();

        if (usuario.getCartaoGeral().getBeneficio() == true && tipo == 2) {
            setValorCredito(usuario.getCartaoGeral().getBeneficio());
            usuario.getCartaoGeral().addCreditosBeneficio(usuario, valorCredito);

        } else if (usuario.getCartaoGeral().getBeneficio() == true && tipo == 1) {
            usuario.getCartaoGeral().addCreditosGeral(usuario, valorCredito);

        } else
            usuario.getCartaoGeral().addCreditosGeral(usuario, valorCredito);


        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sqlInsertUser = "UPDATE cartaogeral SET saldoGeral = ?, saldoBeneficio = ? WHERE cpfUser = ?";
            preparedStatement = connection.prepareStatement(sqlInsertUser);

            preparedStatement.setInt(1, usuario.getCartaoGeral().getSaldoGeral());
            preparedStatement.setInt(2, usuario.getCartaoGeral().getSaldoBeneficio());
            preparedStatement.setString(3, usuario.getCpf());

            // Execute a instrução SQL de atualização
            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cartão geral atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar o cartão geral.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        private void solicitaBeneficio(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Você precisa estar logado para solicitar benefício.");
            return;
        }
        if (usuario.getCartaoGeral().getBeneficio()) {
            System.out.println("Você já tem benefício.");
            return;
        }
        // Fazer tabela para documentos do benefício
        System.out.println("Anexe os documentos: ");
        String docs = " - "; // não sei como anexar documentos

        FormularioBeneficio formularioBeneficio = new FormularioBeneficio();
        // enviarAnaliseCmtu(formularioBeneficio);
    }

    public void makeMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nSistema LondriBus - Escolha sua opção: ");
        System.out.println("1 - Cadastrar novo usuário\n" +
                "2 - Logar usuário\n" +
                "3 - Comprar créditos\n" +
                "4 - Solicitar benefício\n" +
                "0 - SAIR\n");
        int op = sc.nextInt();

        Usuario usuario = null;
        while (op != 0) {
            switch (op) {
                case 1:
                    usuario = criarUsuario(sc);
                    if (usuario == null)
                        System.out.println("Falha ao inserir usuário. Tente novamente");
                    else
                        System.out.print("\nUsuário " + usuario.getNome() + " criado e logado!\n");
                    break;

                    case 2:
                        if (usuario != null) break;
                    usuario = logarUsuario(sc);
                    if (usuario == null)
                        System.out.println("Nenhum usuário encontrado com o CPF informado. Tente novamente\n");
                    else
                        System.out.print("\nUsuário " + usuario.getNome() + " logado!\n");
                    break;

                case 3:
                    compraCredito(usuario, sc);
                    break;

                case 4:
                    solicitaBeneficio(usuario);
                    break;

                default:
                    System.out.println("Opção indisponível. Tente novamente.");
                    break;
            }

            System.out.println("\nSistema LondriBus - Escolha sua opção: ");
            System.out.println("1 - Cadastrar novo usuário\n" +
                    "2 - Logar usuário\n" +
                    "3 - Comprar créditos\n" +
                    "4 - Solicitar benefício\n" +
                    "0 - SAIR\n");
            op = sc.nextInt();
        }
        sc.close();
    }

    public void printStatusCartao(CartaoGeral cg) {
        if (cg.getBeneficio()) {
            System.out.println("CARTAO ESTUDANTE");
        } else {
            System.out.println("CARTAO GERAL");
        }
    }
}
