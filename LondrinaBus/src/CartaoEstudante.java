import java.util.Scanner;
import java.util.UUID;

public class CartaoEstudante extends CartaoGeral {
    private String nomeEstudante;
    private int matricula;

    public CartaoEstudante(int numUnico, int saldoCredito, double valorCredito, String nomeEstudante, int matricula){
        super(numUnico, saldoCredito, valorCredito);
        this.nomeEstudante = nomeEstudante;
        this.matricula = matricula;
    }

    @Override
    public void comprarCreditos(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de créditos desejada: ");
        int quantidadeDesejada = scanner.nextInt();
        scanner.nextLine();

        if(quantidadeDesejada > 0 ){
            quantidadeDesejada += quantidadeDesejada;

            double valorTotal = quantidadeDesejada + (valorCredito * 0.5);

            saldoCredito += valorTotal;
            System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
        }   else {
            System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
        }
    }
    
    public String getNomeEstudante(){
        return nomeEstudante;
    }

    public int getMatricula(){
        return matricula;
    }

    public static void main(String [] args){
        CartaoEstudante cartaoEstudante = new CartaoEstudante(12345, 0, 4.80, "Jader", 123456);

        System.out.println("Código NFC: " + cartaoEstudante.getCodigoNFC());
        System.out.println("Número Único: " + cartaoEstudante.getNumUnico());
        System.out.println("Saldo de Crédito: " + cartaoEstudante.getSaldoCredito());
        System.out.println("Valor de Cada Crédito: " + cartaoEstudante.getValorCredito());
        System.out.println("Nome do Estudante: " + cartaoEstudante.getNomeEstudante());
        System.out.println("Matrícula do Estudante: " + cartaoEstudante.getMatricula());

        cartaoEstudante.comprarCreditos(); // Solicita a quantidade de créditos desejada
        System.out.println("Quantidade de Créditos após compra: " + cartaoEstudante.getQuantidadeCreditos());
    }
}
