import java.util.Scanner;
import java.util.UUID;

public class CartaoGeral {
    private String codigoNFC;
    private int numUnico;
    protected int saldoCredito;
    private int quantidadeCreditos;
    protected double valorCredito;

    public CartaoGeral(int numUnico, int saldoCredito, double valorCredito) {
        this.codigoNFC = UUID.randomUUID().toString(); //Gerar um valor randon para o NFC do cartão
        this.numUnico = numUnico; //Número do cartão
        this.saldoCredito = saldoCredito; //Saldo de Crédito
        this.quantidadeCreditos = 0; //Quantidade inicial de créditos na conta
        this.valorCredito = valorCredito; //Preço do crédito
    }

    public CartaoGeral() {
    }

    //Compra de Crédito (Provisório)
    public void comprarCreditos() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de créditos desejada: ");
        int quantidadeDesejada = scanner.nextInt();
        scanner.nextLine(); 

        if (quantidadeDesejada > 0) {
            quantidadeCreditos += quantidadeDesejada;
            double valorTotal = quantidadeDesejada * valorCredito;
            saldoCredito += valorTotal;
            System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
        } else {
            System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
        }
    }

    public String getCodigoNFC() {
        return codigoNFC;
    }

    public int getNumUnico() {
        return numUnico;
    }

    public int getSaldoCredito() {
        return saldoCredito;
    }

    public int getQuantidadeCreditos() {
        return quantidadeCreditos;
    }

    public double getValorCredito() {
        return valorCredito;
    }

    public static void main(String[] args) {
        //Menu com as informações do cartão
        CartaoGeral cartao = new CartaoGeral(12345, 0, 4.80);
        System.out.println("Código NFC: " + cartao.getCodigoNFC()); 
        System.out.println("Número Único: " + cartao.getNumUnico());
        System.out.println("Saldo de Crédito: " + cartao.getSaldoCredito());
        System.out.println("Valor de Cada Crédito: " + cartao.getValorCredito());

        cartao.comprarCreditos(); // Solicita a quantidade de créditos desejada
        System.out.println("Quantidade de Créditos após compra: " + cartao.getQuantidadeCreditos());
    }
}
