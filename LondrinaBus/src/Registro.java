public class Registro {
    private String login;
    private String senha;
    private String nome;
    private String cep;
    private String cpf;
    private String endereco;

    public Registro() {}
    public Registro(String login, String senha, String nome, String cep, String cpf, String endereco){
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

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCep() {
        return cep;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
