package aranoua.edu.atividadeFinal.dto;

// Importação da classe RevistaCientifica, para conversão do DTO para o modelo
import aranoua.edu.atividadeFinal.model.RevistaCientifica;

/*
 * Classe DTO de entrada para dados de Revista Científica.
 * Representa as informações de uma revista científica que podem ser fornecidas pelo usuário.
 */
public class RevistaCientificaInputDTO {

    // Atributos do DTO
    private String nome; // Nome da revista científica
    private String issn; // Código ISSN da revista científica

    // Construtor vazio
    public RevistaCientificaInputDTO() {
    }

    // Construtor com parâmetros para inicializar o nome e o ISSN da revista
    public RevistaCientificaInputDTO(String nome, String issn) {
        this.nome = nome;
        this.issn = issn;
    }

    // Getter e Setter para o nome da revista
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e Setter para o ISSN da revista
    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    /*
     * Constrói uma instância do modelo RevistaCientifica a partir dos dados do DTO.
     * @return um objeto RevistaCientifica com os dados do DTO.
     */
    public RevistaCientifica build() {
        RevistaCientifica revistaCientifica = new RevistaCientifica();
        revistaCientifica.setNome(this.nome);  // Define o nome no modelo
        revistaCientifica.setIssn(this.issn);  // Define o ISSN no modelo
        return revistaCientifica;
    }
}
