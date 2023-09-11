
public class Usuario {
    private Registro dadosPessoais;
    private CartaoGeral cartaoUser;

    public Usuario(Registro r, CartaoGeral cg) {
        this.dadosPessoais = r;
        this.cartaoUser = cg;
    }

    public Registro getDadosPessoais() { return this.dadosPessoais; }
    public CartaoGeral getCartaoUser() { return this.cartaoUser; }

}

