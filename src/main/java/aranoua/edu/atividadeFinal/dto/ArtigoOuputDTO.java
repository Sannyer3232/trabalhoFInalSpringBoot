package aranoua.edu.atividadeFinal.dto;

import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.model.Autor;

import java.util.ArrayList;
import java.util.List;
/**
 * Classe DTO de saída para Artigo.
 * Responsável por fornecer os dados de um artigo para serem retornados ao cliente.
 */
public class ArtigoOuputDTO {

    // Atributos do DTO
    private Long id; // Identificador único do artigo
    private String titulo; // Título do artigo
    private String ano; // Ano de publicação do artigo
    private List<String> autores = new ArrayList<>(); // Lista de nomes dos autores do artigo
    private String revista;

    public ArtigoOuputDTO() {
    }


    /**
     * Construtor que inicializa o DTO com os dados de um objeto Artigo.
     * Converte o modelo Artigo para o formato de DTO de saída.
     *
     */
    public ArtigoOuputDTO(Artigo artigo) {
        this.id = artigo.getId();  // Define o ID do artigo
        this.titulo = artigo.getTitulo();  // Define o título do artigo
        this.ano = artigo.getAno();  // Define o ano de publicação

        // Extrai os nomes dos autores do artigo
        List<Autor> autores = artigo.getAutores();
        List<String> nomeAutores = new ArrayList<>();
        for (Autor autor : autores) {
            nomeAutores.add(autor.getNome());
        }
        this.autores = nomeAutores;

        // Define o nome da revista onde o artigo foi publicado
        this.revista = artigo.getRevista().getNome();
    }
    // Getters e Setters para os atributos do DTO
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

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public String getRevista() {
        return revista;
    }

    public void setRevista(String revista) {
        this.revista = revista;
    }
}
