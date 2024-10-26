package aranoua.edu.atividadeFinal.service;

// Importações necessárias para o funcionamento da classe
import aranoua.edu.atividadeFinal.dto.RevistaCientificaInputDTO;
import aranoua.edu.atividadeFinal.dto.RevistaCientificaOutputDTO;
import aranoua.edu.atividadeFinal.model.RevistaCientifica;
import aranoua.edu.atividadeFinal.repository.RevistaCientificaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Classe de serviço para gerenciamento de revistas científicas, contendo métodos para
 * listar, buscar, criar, atualizar e deletar revistas científicas no sistema.
 */
@Service // Indica que esta classe é um serviço Spring
public class RevistaCientificaService {

    @Autowired // Injeta o repositório de revistas científicas
    private RevistaCientificaRespository revistaCientificaRespository;

    /*
     * Retorna uma lista de todas as revistas científicas no formato DTO.
     */
    public List<RevistaCientificaOutputDTO> list(){
        List<RevistaCientifica> revistaCientificas = revistaCientificaRespository.findAll(); // Busca todas as revistas científicas no repositório

        List<RevistaCientificaOutputDTO> revistaCientificasOutputDTO = new ArrayList<>();

        // Converte cada revista científica para seu respectivo DTO
        for(RevistaCientifica revista : revistaCientificas){
            revistaCientificasOutputDTO.add(new RevistaCientificaOutputDTO(revista));
        }

        return revistaCientificasOutputDTO; // Retorna a lista de revistas científicas DTO
    }

    /*
     * Busca uma revista científica pelo seu ID.
     */
    public RevistaCientificaOutputDTO getById(Long id){
        Optional<RevistaCientifica> possivelRevista = revistaCientificaRespository.findById(id); // Busca a revista pelo ID
        if(possivelRevista.isPresent()){ // Verifica se a revista existe
            return new RevistaCientificaOutputDTO(possivelRevista.get()); // Retorna a revista no formato DTO
        } else {
            return null; // Retorna null se a revista não foi encontrada
        }
    }

    /*
     * Cria uma nova revista científica no sistema a partir dos dados fornecidos.
     */
    public RevistaCientificaOutputDTO create(RevistaCientificaInputDTO revistaCientificaInputDTO){
        try{
            // Constrói o objeto RevistaCientifica a partir do DTO
            RevistaCientifica revistaCientifica = revistaCientificaInputDTO.build();

            // Salva a revista no repositório e retorna no formato DTO
            RevistaCientifica revistaCientificaSalvaNoBD = revistaCientificaRespository.save(revistaCientifica);

            return new RevistaCientificaOutputDTO(revistaCientificaSalvaNoBD);

        } catch(Exception e){ // Captura exceções
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Atualiza uma revista científica existente com novos dados
     */
    public RevistaCientificaOutputDTO update(Long id, RevistaCientificaInputDTO revistaCientificaInputDTO){
        try{
            Optional<RevistaCientifica> possivelRevista = revistaCientificaRespository.findById(id); // Busca a revista pelo ID

            if(possivelRevista.isPresent()){ // Verifica se a revista existe
                RevistaCientifica revistaCientifica = possivelRevista.get(); // Obtém a revista

                // Atualiza os atributos da revista
                revistaCientifica.setNome(revistaCientificaInputDTO.getNome());
                revistaCientifica.setIssn(revistaCientificaInputDTO.getIssn());

                // Salva a revista atualizada no repositório e retorna no formato DTO
                RevistaCientifica revistaCientificaAlterado = revistaCientificaRespository.save(revistaCientifica);

                return new RevistaCientificaOutputDTO(revistaCientificaAlterado);
            } else {
                return null; // Retorna null se a revista não foi encontrada
            }
        } catch(Exception e){ // Captura exceções
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Deleta uma revista científica do sistema.
     */
    public void delete(Long id) {
        try {
            revistaCientificaRespository.deleteById(id); // Deleta a revista pelo ID
        } catch (Exception e) { // Captura exceções e imprime o stack trace
            e.printStackTrace();
        }
    }
}
