package Model;

public class GerenciadorSAC {

    public void inicioAtendimento() {
        System.out.println("Atendimento iniciado. Redirecionando para um atendente, aguarde um momento, por favor!");
    }
    public String armazenarSac(String protocolo, String data) {

        return "Protocolo: " + protocolo + ", Data: " + data;
    }
}