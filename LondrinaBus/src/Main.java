import Controller.GerenciadorAppOnibus;

public class Main {
    public static void main(String[] args) {
        GerenciadorAppOnibus gerenciadorAppOnibus = new GerenciadorAppOnibus();

        gerenciadorAppOnibus.queryUsuario();
        gerenciadorAppOnibus.queryCartaoGeral();

        gerenciadorAppOnibus.makeMenu();
    }
}
