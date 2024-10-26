package aranoua.edu.atividadeFinal.service;

// Importações necessárias para o funcionamento da classe
import aranoua.edu.atividadeFinal.dto.ArtigoInputDTO;
import aranoua.edu.atividadeFinal.dto.ArtigoOuputDTO;
import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.model.Autor;
import aranoua.edu.atividadeFinal.repository.ArtigoRepository;
import aranoua.edu.atividadeFinal.repository.AutorRepository;
import aranoua.edu.atividadeFinal.repository.RevistaCientificaRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 * Classe de serviço para gerenciamento de artigos, contendo métodos para
 * listar, buscar, criar, atualizar e deletar artigos no sistema.
 */
@Service // Indica que esta classe é um serviço Spring
public class ArtigoService {

    @Autowired // Injeta o repositório de artigos
    private ArtigoRepository artigoRepository;

    @Autowired // Injeta o repositório de autores
    private AutorRepository autorRepository;

    @Autowired // Injeta o repositório de revistas científicas
    private RevistaCientificaRespository revistaCientificaRespository;

    /*
     * Retorna uma lista de todos os artigos no formato DTO.
     *
     */
    public List<ArtigoOuputDTO> list(){
        List<Artigo> artigos = artigoRepository.findAll(); // Busca todos os artigos no repositório

        List<ArtigoOuputDTO> artigoOuputDTOs = new ArrayList<>();

        // Converte cada artigo para seu respectivo DTO
        for (Artigo artigo : artigos) {
            artigoOuputDTOs.add(new ArtigoOuputDTO(artigo));
        }

        return artigoOuputDTOs; // Retorna a lista de artigos DTO
    }

    /*
     * Busca um artigo pelo seu ID.
     */
    public ArtigoOuputDTO getById(Long id){
        Optional<Artigo> artigo = artigoRepository.findById(id); // Busca o artigo pelo ID

        return new ArtigoOuputDTO(artigo.get()); // Retorna o artigo no formato DTO
    }

    /*
     * Cria um novo artigo no sistema a partir dos dados fornecidos.
     *
     */
    public ArtigoOuputDTO create(ArtigoInputDTO artigoInputDTO){
        try{
            // Constrói o objeto Artigo a partir do DTO
            Artigo artigo = artigoInputDTO.build(autorRepository, revistaCientificaRespository);

            // Salva o artigo no repositório e retorna no formato DTO
            return new ArtigoOuputDTO(artigoRepository.save(artigo));

        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Atualiza um artigo existente com novos dados.
     */
    public ArtigoOuputDTO update(Long id , ArtigoInputDTO artigoInputDTO){
        try{
            Optional<Artigo> possivelArtigo = artigoRepository.findById(id); // Busca o artigo pelo ID

            if(possivelArtigo.isPresent()){ // Verifica se o artigo existe
                Artigo artigo = possivelArtigo.get(); // Obtém o artigo

                // Atualiza os atributos do artigo
                artigo.setTitulo(artigoInputDTO.getTitulo());
                artigo.setAno(artigoInputDTO.getAno());

                List<Autor> autores = new ArrayList<>();
                // Busca os autores pelo nome e os adiciona à lista
                for (String autor : artigoInputDTO.getAutores()) {
                    autores.add(autorRepository.findByNome(autor));
                }
                artigo.setAutores(autores); // Define a nova lista de autores
                artigo.setRevista(revistaCientificaRespository.findByNome(artigoInputDTO.getRevista())); // Define a revista

                return new ArtigoOuputDTO(artigoRepository.save(artigo)); // Retorna o artigo atualizado no formato DTO
            } else {
                return null; // Retorna null se o artigo não foi encontrado
            }
        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
            return null; // Retorna null em caso de erro
        }
    }

    /*
     * Busca todos os artigos de um autor específico.
     */
    public List<ArtigoOuputDTO> findByAutor(Long id) {
        List<Artigo> artigos = autorRepository.findById(id).get().getArtigos(); // Busca os artigos do autor

        List<ArtigoOuputDTO> artigoOuputDTOs = new ArrayList<>();

        // Converte cada artigo para seu respectivo DTO
        for (Artigo artigo : artigos) {
            artigoOuputDTOs.add(new ArtigoOuputDTO(artigo));
        }

        return artigoOuputDTOs; // Retorna a lista de artigos DTO
    }

    /*
     * Deleta um artigo do sistema.
     */
    public void delete(Long id){
        try{
            artigoRepository.deleteById(id); // Deleta o artigo pelo ID
        }catch(Exception e){ // Captura exceções e imprime o stack trace
            e.printStackTrace();
        }
    }
}
