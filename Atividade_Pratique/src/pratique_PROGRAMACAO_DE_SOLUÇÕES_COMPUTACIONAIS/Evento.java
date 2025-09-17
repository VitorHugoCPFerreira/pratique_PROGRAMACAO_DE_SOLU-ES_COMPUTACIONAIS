package pratique_PROGRAMACAO_DE_SOLUÇÕES_COMPUTACIONAIS;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    
    String nome;
    String endereco;
    Categoria categoria;
    LocalDateTime horario;
    String descricao;
    List<Usuario> participantes;

    public Evento(String nome, String endereco, Categoria categoria, LocalDateTime horario, String descricao) {
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
        this.participantes = new ArrayList<>();
    }

    public void adicionarParticipante(Usuario usuario) {
        if (!participantes.contains(usuario)) participantes.add(usuario);
    }

    public void removerParticipante(Usuario usuario) {
        participantes.remove(usuario);
    }

    public boolean estaParticipando(Usuario usuario) {
        return participantes.contains(usuario);
    }

    // Getters
    public LocalDateTime getHorario() { return horario; }
    public String getNome() { return nome; }
    public String getEndereco() { return endereco; }
    public Categoria getCategoria() { return categoria; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() {
        return nome + " | " + endereco + " | " + categoria + " | " + horario + " | " + descricao;
    }
}