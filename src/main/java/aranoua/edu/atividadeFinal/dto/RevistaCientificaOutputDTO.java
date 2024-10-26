package aranoua.edu.atividadeFinal.dto;

// Importação das classes necessárias para a conversão do objeto RevistaCientifica e manipulação da lista de artigos
import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.model.RevistaCientifica;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe DTO de saída para Revista Científica.
 * Responsável por fornecer os dados de uma revista científica e seus artigos associados para serem retornados ao cliente.
 */
public class RevistaCientificaOutputDTO {

    // Atributos do DTO
    private Long id; // Identificador único da revista
    private String nome; // Nome da revista
    private String issn; // ISSN da revista
    private List<String> artigos = new ArrayList<>(); // Lista de títulos dos artigos da revista

    // Construtor padrão
    public RevistaCientificaOutputDTO() {
    }

    /*
     * Construtor que inicializa o DTO com os dados de um objeto RevistaCientifica.
     * Converte o modelo RevistaCientifica para o formato de DTO de saída.
     *
     * @param revistaCientifica o objeto RevistaCientifica do qual os dados serão extraídos
     */
    public RevistaCientificaOutputDTO(RevistaCientifica revistaCientifica) {
        this.id = revistaCientifica.getId(); // Define o ID da revista
        this.nome = revistaCientifica.getNome(); // Define o nome da revista
        this.issn = revistaCientifica.getIssn(); // Define o ISSN da revista

        // Extrai os títulos dos artigos associados à revista
        List<Artigo> artigos = revistaCientifica.getArtigos();
        List<String> artigoTitulo = new ArrayList<>();
        for (Artigo artigo : artigos) {
            artigoTitulo.add(artigo.getTitulo()); // Adiciona o título de cada artigo à lista
        }
        this.artigos = artigoTitulo; // Define a lista de títulos de artigos
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

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public List<String> getArtigos() {
        return artigos;
    }

    public void setArtigos(List<String> artigos) {
        this.artigos = artigos;
    }
}
