package aranoua.edu.atividadeFinal.model;

import jakarta.persistence.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.ArrayList;
import java.util.List;

//Classe que representa a Entidade de Afiliação
@Entity(name ="tabafiliacao")
public class Afiliacao {
    //Atributos da entidade que serão mapeados como campos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="aflnome", nullable=false)
    private String nome;
    @Column(name ="aflsigla", nullable=false)
    private String sigla;
    @Column(name ="aflreferencia", nullable=false, unique = true)
    private String referencia;

    @OneToMany(mappedBy = "afiliacao")
    private List<Autor> autores = new ArrayList<>();

    public Afiliacao() {
    }

    public Afiliacao(Long id, String nome, String sigla, String referencia) {
        this.id = id;
        this.nome = nome;
        this.sigla = sigla;
        this.referencia = referencia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}
