package org.LondriBus.Model;


import java.util.Scanner;
import java.util.UUID;

public class CartaoGeral {
    protected String codigoNFC;
    protected int numUnico;
    protected int saldoGeral;
    protected int saldoBeneficio;
    protected boolean beneficio;

    // usar quando for de fato criar um novo usuário
    public CartaoGeral() {
        this.codigoNFC = UUID.randomUUID().toString(); //Gerar um valor random para o NFC do cartão
         this.saldoGeral = 0; //Saldo de crédito geral
         this.saldoBeneficio = 0; //Saldo de crédito benefício
         this.beneficio = false;
    }

    public CartaoGeral(int numUnico, String codigoNFC, int saldoGeral, int saldoBeneficio, boolean beneficio) {
        this.codigoNFC = codigoNFC;
        this.numUnico = numUnico;
        this.saldoGeral = saldoGeral;
        this.saldoBeneficio = saldoBeneficio;
        this.beneficio = beneficio;
    }

    //Compra de Crédito (Provisório), valor vem do Controller
    public void addCreditosGeral(Usuario usuario, double valor, int quantidadeDesejada) {
        Scanner scanner = new Scanner(System.in);
        CartaoGeral cg = usuario.getCartaoGeral();

        if (quantidadeDesejada > 0) {
            double valorTotal = quantidadeDesejada * valor;
            cg.setSaldoGeral(cg.getSaldoGeral() + quantidadeDesejada);
            System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
            System.out.println("Saldo atual geral: " + cg.getSaldoGeral());
        } else {
            System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
        }
    }

    //Praticamente igual à addCreditosGeral(), pode deixar em uma mesma função ou não
    public void addCreditosBeneficio(Usuario estudante, double valor, int quantidadeDesejada){
        CartaoGeral ce = estudante.getCartaoGeral();
        if (ce.getBeneficio()) {

            if (quantidadeDesejada > 0) {
                double valorTotal = quantidadeDesejada * valor;
                ce.setSaldoBeneficio(ce.getSaldoBeneficio() + quantidadeDesejada);
                System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
                System.out.println("Saldo atual com benefício: " + ce.getSaldoBeneficio());
            } else {
                System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
            }
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

    public void setSaldoGeral(int s) {
        this.saldoGeral = s;
    }
    
    public int getSaldoGeral() {
        return saldoGeral;
    }

    public int getSaldoBeneficio() {
        return saldoBeneficio;
    }

    public void setSaldoBeneficio(int saldoBeneficio) {
        this.saldoBeneficio = saldoBeneficio;
    }

    public void setBeneficio(boolean b) {
        this.beneficio = b;
    }
    
    public boolean getBeneficio() {
        return this.beneficio;
    }


}
