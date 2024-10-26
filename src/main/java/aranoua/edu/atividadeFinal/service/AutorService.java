package aranoua.edu.atividadeFinal.service;

// Importações necessárias para o funcionamento da classe
import aranoua.edu.atividadeFinal.dto.AutorInputDTO;
import aranoua.edu.atividadeFinal.dto.AutorOutputDTO;
import aranoua.edu.atividadeFinal.model.Autor;
import aranoua.edu.atividadeFinal.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Classe de serviço para gerenciamento de autores, contendo métodos para
 * listar, buscar, criar, atualizar e deletar autores no sistema.
 */
@Service // Indica que esta classe é um serviço Spring
public class AutorService {

    @Autowired // Injeta o repositório de autores
    private AutorRepository autorRepository;

    /*
     * Retorna uma lista de todos os autores no formato DTO.
     *
     */
    public List<AutorOutputDTO> list(){
        List<Autor> autores = autorRepository.findAll(); // Busca todos os autores no repositório
        System.out.println(autores); // Imprime a lista de autores no console para debug

        List<AutorOutputDTO> autoresOutputDTO = new ArrayList<>();

        // Converte cada autor para seu respectivo DTO
        for(Autor autor : autores){
            autoresOutputDTO.add(new AutorOutputDTO(autor));
        }

        return autoresOutputDTO; // Retorna a lista de autores DTO
    }

    /*
     * Busca um autor pelo seu ID.
     */
    public AutorOutputDTO getById(Long id){
        Optional<Autor> autor = autorRepository.findById(id); // Busca o autor pelo ID

        return new AutorOutputDTO(autor.get()); // Retorna o autor no formato DTO
    }

    /*
     * Cria um novo autor no sistema a partir dos dados fornecidos.
     */
    public AutorOutputDTO create(AutorInputDTO autorInputDTO){
        try{
            // Constrói o objeto Autor a partir do DTO
            Autor autor = autorInputDTO.build();

            // Salva o autor no repositório e retorna no formato DTO
            Autor autorSalvoNoBD = autorRepository.save(autor);

            return new AutorOutputDTO(autorSalvoNoBD);
        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Atualiza um autor existente com novos dados.
     */
    public AutorOutputDTO update(Long id, AutorInputDTO autorInputDTO){
        try{
            Optional<Autor> possivelAutor  = autorRepository.findById(id); // Busca o autor pelo ID

            if(possivelAutor.isPresent()){ // Verifica se o autor existe
                Autor autor = possivelAutor.get(); // Obtém o autor

                // Atualiza os atributos do autor
                autor.setNome(autorInputDTO.getNome());
                autor.setAfiliacao(autorInputDTO.getAfiliacao());

                // Salva o autor atualizado no repositório e retorna no formato DTO
                Autor autorAlterado = autorRepository.save(autor);

                return new AutorOutputDTO(autorAlterado);
            } else {
                return null; // Retorna null se o autor não foi encontrado
            }
        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Deleta um autor do sistema
     */
    public Boolean delete(Long id){
        try{
            autorRepository.deleteById(id); // Deleta o autor pelo ID
            return true; // Retorna true se a deleção foi bem-sucedida
        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
            return false; // Retorna false em caso de erro
        }
    }
}
