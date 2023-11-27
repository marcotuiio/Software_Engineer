package org.LondriBus.Controller;

import org.LondriBus.Model.*;
import org.LondriBus.Model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

@Controller
public class GerenciadorAppOnibus {
    private double valorCredito = 4.8; //fazer getters e setters, mais vale um na mão do que dois voando.
    private ArrayList<CartaoGeral> cartoes = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private DatabaseManagerApp databaseManager = new DatabaseManagerApp();
    PreparedStatement preparedStatement;


    @GetMapping("/pio")
    public String queryUsuario(Model model) {

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
        model.addAttribute("usuarios", usuarios);
        return "todos-usuarios";
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

    @GetMapping("/form-criar-usuario")
    public String formCriarUsuario(Usuario usuario) {
        return "form-novo-usuario";
    }

    @PostMapping("/criar-usuario")
    public String criarUsuario(Usuario usuario, BindingResult bindingResult, Model model) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();

        try {
            String sqlInsertUser = "INSERT INTO usuario (login, senha, nome, cep, cpf, endereco) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlInsertUser);

            preparedStatement.setString(1, usuario.getLogin());
            preparedStatement.setString(2, usuario.getSenha());
            preparedStatement.setString(3, usuario.getNome());
            preparedStatement.setString(4, usuario.getCep());
            preparedStatement.setString(5, usuario.getCpf());
            preparedStatement.setString(6, usuario.getEndereco());

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
                return "redirect:/";
            }

            model.addAttribute("userName", usuario.getNome());
            model.addAttribute("usuario", usuario);
            model.addAttribute("cartaoGeral", usuario.getCartaoGeral());
            return "usuario-logado";

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/";
    }

    @GetMapping(value = {"/index", "/"})
    public String mostrarTelaInicial(Usuario usuario) {
        return "index";
    }

    @GetMapping("/logout")
    public String logoutUsuario(Usuario usuario, Model model) {
        usuario = null;
        return "index";
    }

    @PostMapping("/logar-usuario")
    public String logarUsuario(Usuario usuario, BindingResult bindingResult, Model model) {
        String login = usuario.getLogin();
        String senha = usuario.getSenha();
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();
//        System.out.printf("\nchegou aqui %s %s\n", login, senha);
        try {
            String sql = "SELECT * FROM usuario WHERE login = ? and senha = ?";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, senha);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String cep = resultSet.getString("cep");
                String endereco = resultSet.getString("endereco");

                Usuario usuarioEncontrado = new Usuario(login, senha, nome, cep, cpf, endereco);

                CartaoGeral cartaoGeral = obterCartao(usuarioEncontrado.getCpf());

                usuarioEncontrado.setCartaoGeral(cartaoGeral);
//                System.out.printf("\nUser %s cpf %s cartao %s\n", usuarioEncontrado.getNome(), usuarioEncontrado.getCpf(), cartaoGeral.getCodigoNFC());

                model.addAttribute("userName", usuarioEncontrado.getNome());
                model.addAttribute("usuario", usuarioEncontrado);
                model.addAttribute("cartaoGeral", usuarioEncontrado.getCartaoGeral());
                return "usuario-logado";

            } else {
                model.addAttribute("erro", "Usuario não encontado.");
                return "redirect:/";
            }

        } catch (SQLException ex) {
            Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
        }
        // printar no index que nao achou o usuario
        model.addAttribute("erro", "Usuario não encontado.");
        return "redirect:/";
    }

    public CartaoGeral obterCartao(String cpf) {
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

    public Usuario getUsuario(String cpf) {
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();
        Usuario usuarioEncontrado = null;

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

                usuarioEncontrado = new Usuario(login, senha, nome, cep, cpf, endereco);

                CartaoGeral cartaoGeral = obterCartao(usuarioEncontrado.getCpf());

                usuarioEncontrado.setCartaoGeral(cartaoGeral);

            } else {
                return null;
            }

            } catch(SQLException ex){
                Logger.getLogger(GerenciadorAppOnibus.class.getName()).log(Level.SEVERE, null, ex);
            }
        return usuarioEncontrado;
    }

    @GetMapping("/form-comprar-credito/{cpf}")
    public String formComprarCredito(@PathVariable("cpf") String cpf, Model model) {
        model.addAttribute("compra", new Compras());
        model.addAttribute("userCpf", cpf);
        return "comprar-creditos";
    }

    @PostMapping("/compra-credito/{cpf}")
    public String compraCredito(@PathVariable("cpf") String cpf, Compras compra, Model model) {

        // DAR UM JEITO DE MOSTRAR O VALOR DA COMPRA

        Usuario usuario = getUsuario(cpf);
        System.out.printf("Comprando para: %s cartao %s\n", usuario.getNome(), usuario.getCartaoGeral().getCodigoNFC());
        if (usuario == null) {
            model.addAttribute("erro","Você precisa estar logado para comprar créditos.");
            return "redirect:/";
        }
        boolean comBeneficio = compra.getTipo().equals("comBeneficio");
        boolean semBeneficio = compra.getTipo().equals("semBeneficio");

        if (usuario.getCartaoGeral().getBeneficio() == true && comBeneficio) {
            setValorCredito(usuario.getCartaoGeral().getBeneficio());
            usuario.getCartaoGeral().addCreditosBeneficio(usuario, valorCredito, compra.getQntdCreditos());

        } else if (usuario.getCartaoGeral().getBeneficio() == true && semBeneficio) {
            valorCredito = 4.8;
            usuario.getCartaoGeral().addCreditosGeral(usuario, valorCredito, compra.getQntdCreditos());

        } else {
            valorCredito = 4.8;
            usuario.getCartaoGeral().addCreditosGeral(usuario, valorCredito, compra.getQntdCreditos());
        }

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

        model.addAttribute("usuario", usuario);
        model.addAttribute("cartaoGeral", usuario.getCartaoGeral());
        return "usuario-logado";
    }

    @GetMapping("/form-beneficio/{cpf}")
    public String formBeneficio(@PathVariable("cpf") String cpf, Model model) {
        model.addAttribute("formBeneficio", new FormularioBeneficio());
        model.addAttribute("cpf", cpf);
        return "form-beneficio";
    }

    @PostMapping("/beneficio/{cpf}")
    public String solicitaBeneficio(@PathVariable("cpf") String cpf, FormularioBeneficio formBeneficio, Model model) {
        Usuario usuario = getUsuario(cpf);
        System.out.printf("Solicitando beneficio para: %s cartao %s\n", usuario.getNome(), usuario.getCartaoGeral().getCodigoNFC());
        if (usuario == null) {
            model.addAttribute("erro","Você precisa estar logado para solicitar beneficio.");
            return "redirect:/";
        }
        if (usuario.getCartaoGeral().getBeneficio()) {
            model.addAttribute("erro", "Você já tem beneficio!");
            model.addAttribute("usuario", usuario);
            model.addAttribute("cartaoGeral", usuario.getCartaoGeral());
            return "usuario-logado";
        }

//         boolean aprovado = enviarCMTU(formBeneficios);
        boolean aprovado = false;
         if (!aprovado) {
             model.addAttribute("erro", "Sinto muito, sua requisição de beneficio foi negada pela CMTU");
             model.addAttribute("usuario", usuario);
             model.addAttribute("cartaoGeral", usuario.getCartaoGeral());
             return "usuario-logado";
         }

        // INICIO AREA DE SIMULACAO DE APROVAR O BENEFICIO !!!!
        usuario.getCartaoGeral().setBeneficio(true);
        databaseManager.connect();
        Connection connection = databaseManager.getConnection();
        try {
            String sqlInsertUser = "UPDATE cartaogeral SET beneficio = ? WHERE cpfUser = ?";
            preparedStatement = connection.prepareStatement(sqlInsertUser);

            preparedStatement.setBoolean(1, usuario.getCartaoGeral().getBeneficio());
            preparedStatement.setString(2, usuario.getCpf());

            int linhasAfetadas = preparedStatement.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Cartão geral atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar o cartão geral.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // FIM AREA SIMULACAO

        model.addAttribute("usuario", usuario);
        model.addAttribute("cartaoGeral", usuario.getCartaoGeral());
        return "usuario-logado";
    }

    @GetMapping("/sac/{cpf}")
    public String gotoSac(@PathVariable("cpf") String cpf, Model model) {
        model.addAttribute("userName", cpf);
        return "sac";
    }

}
