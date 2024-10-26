package aranoua.edu.atividadeFinal.dto;

// Importação da classe Autor
import aranoua.edu.atividadeFinal.model.Autor;

/*
 * Classe DTO de entrada para os dados de um Autor.
 * Usada para transferir dados relacionados a um autor, incluindo
 * nome e afiliação.
 */
public class AutorInputDTO {

    // Atributos do DTO
    private String nome; // Nome do autor
    private String afiliacao; // Instituição ou afiliação do autor

    // Construtor vazio
    public AutorInputDTO() {}

    // Construtor com parâmetros para inicializar o DTO com dados
    public AutorInputDTO(String nome, String afiliacao) {
        this.nome = nome;
        this.afiliacao = afiliacao;
    }

    // Métodos getter e setter para os atributos

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

    /*
     * Método responsável por construir um objeto Autor a partir dos dados deste DTO.
     * @return Objeto Autor com dados preenchidos a partir do DTO.
     */
    public Autor build() {
        Autor autor = new Autor();

        // Define o nome e a afiliação no objeto Autor
        autor.setNome(this.nome);
        autor.setAfiliacao(this.afiliacao);

        return autor; // Retorna o autor com os dados preenchidos
    }
}
