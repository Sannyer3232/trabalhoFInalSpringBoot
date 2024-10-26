package aranoua.edu.atividadeFinal.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
//Classe modelo Revista Cientifica mapeada como entitade no banco de dados
@Entity(name = "tabRevistaCientifica")
public class RevistaCientifica {
    //Atributo representado com chave primaria no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recnome", nullable = false)
    private String nome;
    @Column(name = "recissn", nullable = false, unique = true)
    private String issn;

    //Lista de Artigos publicados na revista
    @OneToMany(mappedBy = "revista")
    private List<Artigo> artigos = new ArrayList<>();

    //Metodos construtores do objeto da classe modelo Revista Cientifica
    public RevistaCientifica() {
    }



    public RevistaCientifica(Long id, String nome, String issn, List<Artigo> artigos) {
        this.id = id;
        this.nome = nome;
        this.issn = issn;
        this.artigos = artigos;
    }

    //Getters e Setters para manipulação de dados
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

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public List<Artigo> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<Artigo> artigos) {
        this.artigos = artigos;
    }
}
