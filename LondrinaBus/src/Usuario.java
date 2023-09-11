public class Usuario {
    private Registro dadosPessoais;
    private CartaoGeral cartaoUser;
    private CartaoEstudante cartaoPasse;

    public Usuario(Registro r, CartaoGeral cg, CartaoEstudante cp) {
        this.dadosPessoais = r;
        this.cartaoUser = cg;
        this.cartaoPasse = cp;
    }

    public Registro getDadosPessoais() { return this.dadosPessoais; }
    public CartaoGeral getCartaoUser() { return this.cartaoUser; }
    public CartaoEstudante getCartaoPasse() { return this.cartaoPasse; }

}

