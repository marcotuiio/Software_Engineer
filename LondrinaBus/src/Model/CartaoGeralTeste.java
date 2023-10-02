package Model;

import java.util.Scanner;
import java.util.UUID;

public class CartaoGeralTeste {
    protected String codigoNFC;
    protected int numUnico;
    protected int saldoCredito;
    protected double valorCredito;
    protected boolean isEstudante;

    public CartaoGeralTeste(int numUnico) {
        this.codigoNFC = gerarCodigoNFC();
        this.numUnico = numUnico;
        this.saldoCredito = 0;
        this.valorCredito = 4.80;
        this.isEstudante = false;
    }

    private String gerarCodigoNFC() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "").toUpperCase();
    }

    public void addCreditosGeral() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de créditos desejada: ");
        int quantidadeDesejada = scanner.nextInt();
        scanner.nextLine();

        if (quantidadeDesejada > 0) {
            double valorTotal = quantidadeDesejada * valorCredito;
            saldoCredito += quantidadeDesejada;
            System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
            System.out.println("Saldo atual: " + saldoCredito);
        } else {
            System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
        }
    }

    public void setCodigoNFC(String cod) {
        this.codigoNFC = cod;
    }

    public String getCodigoNFC() {
        return codigoNFC;
    }

    public void setNumUnico(int num) {
        this.numUnico = num;
    }

    public int getNumUnico() {
        return numUnico;
    }

    public void setSaldoCredito(int s) {
        this.saldoCredito = s;
    }

    public int getSaldoCredito() {
        return saldoCredito;
    }

    public void setValorCredito(double v) {
        this.valorCredito = v;
    }

    public double getValorCredito() {
        return valorCredito;
    }

    public void setEstudante(boolean b) {
        this.isEstudante = b;
    }

    public boolean getEstudante() {
        return this.isEstudante;
    }

    public void defineAsEstudante() {
        this.isEstudante = true;
        this.valorCredito = 2.40;
    }

    public void printStatusCartao() {
        if (this.isEstudante) {
            System.out.println("CARTAO ESTUDANTE");
        } else {
            System.out.println("CARTAO GERAL");
        }
    }
}