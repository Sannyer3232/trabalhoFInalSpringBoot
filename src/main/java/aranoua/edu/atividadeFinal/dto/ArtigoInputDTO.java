package aranoua.edu.atividadeFinal.dto;

// Importações das classes e pacotes necessários
import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.model.Autor;
import aranoua.edu.atividadeFinal.repository.AutorRepository;
import aranoua.edu.atividadeFinal.repository.RevistaCientificaRespository;

import java.util.ArrayList;
import java.util.List;

/*
 * Classe DTO de entrada para os dados de um Artigo.
 * Usada para transferir dados relacionados a um artigo, incluindo título,
 * ano de publicação, lista de autores e revista associada.
 */
public class ArtigoInputDTO {

    // Atributos do DTO
    private String titulo;
    private String ano;
    private List<String> autores = new ArrayList<>(); // Lista de nomes dos autores
    private String revista; // Nome da revista científica

    // Construtor vazio
    public ArtigoInputDTO() {}

    // Construtor com parâmetros para inicializar o DTO com dados
    public ArtigoInputDTO(String titulo, String ano, List<String> autores, String revista) {
        this.titulo = titulo;
        this.ano = ano;
        this.autores = autores;
        this.revista = revista;
    }

    // Métodos getter e setter para os atributos

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

    /**
     * Método responsável por construir um objeto Artigo a partir dos dados deste DTO.
     * @param autorRepository Repositório usado para buscar objetos Autor pelo nome.
     * @param revistaCientificaRespository Repositório usado para buscar a revista associada pelo nome.
     * @return Objeto Artigo com dados preenchidos a partir do DTO.
     */
    public Artigo build(AutorRepository autorRepository, RevistaCientificaRespository revistaCientificaRespository) {
        Artigo artigo = new Artigo();

        // Configura o título e ano no objeto Artigo
        artigo.setTitulo(this.titulo);
        artigo.setAno(this.ano);

        // Constrói a lista de objetos Autor a partir dos nomes fornecidos no DTO
        List<Autor> autores = new ArrayList<>();
        for (String autor : this.autores) {
            // Busca cada autor no repositório pelo nome e adiciona à lista de autores do artigo
            autores.add(autorRepository.findByNome(autor));
        }
        artigo.setAutores(autores);

        // Busca a revista pelo nome e a associa ao objeto Artigo
        artigo.setRevista(revistaCientificaRespository.findByNome(this.revista));

        return artigo; // Retorna o artigo com os dados preenchidos
    }
}
