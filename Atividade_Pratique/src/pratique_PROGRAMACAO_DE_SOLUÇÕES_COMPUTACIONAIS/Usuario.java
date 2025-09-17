package pratique_PROGRAMACAO_DE_SOLUÇÕES_COMPUTACIONAIS;

import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String nome;
    String email;
    String telefone;
    String endereco;
    String login;
    String senha;

    public Usuario(String nome, String email, String telefone, String endereco, String login, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.login = login;
        this.senha = senha;
    }

    // Getters
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }

    @Override
    public String toString() {
        return nome + " | " + email + " | " + telefone + " | " + endereco + " | Login: " + login;
    }
}