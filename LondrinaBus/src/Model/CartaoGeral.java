package Model;

import java.util.Scanner;
import java.util.UUID;

public class CartaoGeral {
    protected String codigoNFC;
    protected int numUnico;
    protected int saldoGeral;
    protected int saldoBeneficio;
    protected boolean beneficio;
    protected String cpfUser;

    // usar quando for de fato criar um novo usuário
    public CartaoGeral() {
        this.codigoNFC = UUID.randomUUID().toString(); //Gerar um valor random para o NFC do cartão
         this.saldoGeral = 0; //Saldo de crédito geral
         this.saldoBeneficio = 0; //Saldo de crédito benefício
         this.beneficio = false;
    }

    public CartaoGeral(int numUnico, String codigoNFC, int saldoGeral, boolean beneficio, String cpfUser) {
        this.codigoNFC = codigoNFC;
        this.numUnico = numUnico;
        this.saldoGeral = saldoGeral;
//        this.saldoBeneficio = saldoBeneficio;
        this.beneficio = beneficio;
        this.cpfUser = cpfUser;
    }

    //Compra de Crédito (Provisório), valor vem do Controller
    public void addCreditosGeral(double valor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite a quantidade de créditos desejada: ");
        int quantidadeDesejada = scanner.nextInt();
        scanner.nextLine();

        if (quantidadeDesejada > 0) {
            double valorTotal = quantidadeDesejada * valor;
            saldoGeral += valorTotal;
            System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
            System.out.println("Saldo atual: " + saldoGeral);
        } else {
            System.out.println("A quantidade desejada de créditos deve ser maior que zero.");
        }
    }

    //Praticamente igual à addCreditosGeral(), pode deixar em uma mesma função ou não
    public void addCreditosBeneficio(double valor){
        if (this.beneficio) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a quantidade de créditos desejada: ");
            int quantidadeDesejada = scanner.nextInt();
            scanner.nextLine();

            if (quantidadeDesejada > 0) {
                double valorTotal = quantidadeDesejada * valor;
                saldoBeneficio += valorTotal;
                System.out.println(quantidadeDesejada + " crédito(s) comprado(s) com sucesso por um valor total de R$ " + valorTotal);
                System.out.println("Saldo atual: " + saldoBeneficio);
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

    public void setBeneficio(boolean b) {
        this.beneficio = b;
    }
    
    public boolean getBeneficio() {
        return this.beneficio;
    }

    public String getCpfUser() {
        return cpfUser;
    }

    public void setCpfUser(String cpfUser) {
        this.cpfUser = cpfUser;
    }

}
