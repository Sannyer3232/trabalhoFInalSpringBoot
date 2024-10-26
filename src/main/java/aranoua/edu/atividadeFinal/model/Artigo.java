package aranoua.edu.atividadeFinal.model;

import jakarta.persistence.*;

import java.util.List;
//Classe modelo que representa o artigo mapeada como entidade no banco de dados
@Entity(name = "tabartigo")
public class Artigo {
    //Atributo representado com chave primaria no banco de dados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "arttitulo", nullable = false)
    private String titulo;
    @Column(name = "artano", nullable = false)
    private String ano;
    //Lista de autores do artiogo
    @ManyToMany
    private List<Autor> autores;
    //Revista no qual o artigo foi publicado
    @ManyToOne
    private RevistaCientifica revista;

    //Metodos construtores do objeto da classe modelo Artigo
    public Artigo() {
    }

    public Artigo(Long id, String titulo, String ano, List<Autor> autores, RevistaCientifica revista) {
        this.id = id;
        this.titulo = titulo;
        this.ano = ano;
        this.autores = autores;
        this.revista = revista;
    }

    //Getters e Setters para manipulação de dados
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public RevistaCientifica getRevista() {
        return revista;
    }

    public void setRevista(RevistaCientifica revista) {
        this.revista = revista;
    }


}
