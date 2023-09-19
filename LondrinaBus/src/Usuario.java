public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private String cep;
    private String cpf;
    private String endereco;
    private int numUnicoCartao;

    public Usuario() {}
    public Usuario(String login, String senha, String nome, String cep, String cpf, String endereco){
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cep = cep;
        this.cpf = cpf;
        this.endereco = endereco;
        // É necessário adicionar o usuário registrado ao banco de dados, provavelmente onde essa função é chamada
    }

    public boolean logarUsuario(String login, String senha){
        // Usando JPA, verificar se a tupla <login, senha> existe no banco
        // Se existir, retorna true, senão, false.
        return false;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public String getSenha() {
        return senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getCep() {
        return cep;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getCpf() {
        return cpf;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public void setNumUnicoCartao(int n) {
        this.numUnicoCartao = n;
    }
    
    public int getNumUnicoCartao() {
        return this.numUnicoCartao;
    }
}
