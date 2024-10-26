package aranoua.edu.atividadeFinal.dto;

// Importação das classes necessárias para a conversão do objeto Autor e manipulação da lista de artigos
import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.model.Autor;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe DTO de saída para Autor.
 * Responsável por fornecer os dados de um autor e seus artigos associados para serem retornados ao cliente.
 */
public class AutorOutputDTO {

    // Atributos do DTO
    private Long id; // Identificador único do autor
    private String nome; // Nome do autor
    private String afiliacao; // Instituição de afiliação do autor
    private List<String> artigos = new ArrayList<>(); // Lista de títulos dos artigos do autor

    // Construtor padrão
    public AutorOutputDTO() {}

    /*
     * Construtor que inicializa o DTO com os dados de um objeto Autor.
     * Converte o modelo Autor para o formato de DTO de saída.
     *
     */
    public AutorOutputDTO(Autor autor) {
        this.id = autor.getId(); // Define o ID do autor
        this.nome = autor.getNome(); // Define o nome do autor
        this.afiliacao = autor.getAfiliacao(); // Define a afiliação do autor

        // Extrai os títulos dos artigos associados ao autor
        List<String> artigos = new ArrayList<>();
        for (Artigo artigo : autor.getArtigos()) {
            artigos.add(artigo.getTitulo());
        }
        this.artigos = artigos;
    }

    // Getters e Setters para os atributos do DTO
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

    public String getAfiliacao() {
        return afiliacao;
    }

    public void setAfiliacao(String afiliacao) {
        this.afiliacao = afiliacao;
    }

    public List<String> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<String> artigos) {
        this.artigos = artigos;
    }
}
