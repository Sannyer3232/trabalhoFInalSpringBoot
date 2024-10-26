package aranoua.edu.atividadeFinal.controller;

import aranoua.edu.atividadeFinal.dto.RevistaCientificaInputDTO;
import aranoua.edu.atividadeFinal.dto.RevistaCientificaOutputDTO;
import aranoua.edu.atividadeFinal.model.RevistaCientifica;
import aranoua.edu.atividadeFinal.service.RevistaCientificaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.util.List;

/*      Classe de Controle de Revistas Cientificas, responsável por controlar as request e response
    pelo cliente, com os métodos do Mundo Orientado à objeto mapeado de acordo
    com os métodos no HTTP, de forma a montar um CRUD (Create, Read, Update, Delete),
    nesse caso, responsável pelo CRUD de Revistas Cientificas.
*/
@RestController
@RequestMapping("/api/revistacientifica")
public class RevistaCientificaController {

    // Injeta automaticamente o serviço que gerencia operações de negócio para RevistaCientifica.
    @Autowired
    private RevistaCientificaService revistaCientificaService;

    // Endpoint GET para listar todas as revistas científicas.
    // Retorna uma lista de RevistaCientificaOutputDTOs no formato JSON.
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RevistaCientificaOutputDTO>> list(){
        List<RevistaCientificaOutputDTO> revistas = revistaCientificaService.list();

        // Verifica se há revistas disponíveis e retorna OK ou NOT_FOUND.
        if(!revistas.isEmpty()){
            return new ResponseEntity<>(revistas, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint GET para obter uma revista científica específica pelo seu ID.
    // Retorna uma resposta com a revista, incluindo links HATEOAS.
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<RevistaCientificaOutputDTO>> getById(@PathVariable Long id, UriComponentsBuilder uriBuilder) {
        RevistaCientificaOutputDTO possivelRevista = revistaCientificaService.getById(id);

        if(possivelRevista != null){
            // Cria um link para o URI da revista encontrada.
            UriComponents uriComponents = uriBuilder.path("/api/revistacientifica/{id}").buildAndExpand(possivelRevista.getId());
            URI uri = uriComponents.toUri();

            // Define links HATEOAS para listar todas as revistas e deletar a revista atual.
            Link allRevistasLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).list()).withRel("all-revistas");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).delete(possivelRevista.getId())).withRel("delete-revista");

            EntityModel<RevistaCientificaOutputDTO> resource = EntityModel.of(possivelRevista, allRevistasLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint POST para criar uma nova revista científica.
    // Recebe os dados no formato JSON, processa e retorna a revista criada com links HATEOAS.
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<RevistaCientificaOutputDTO>> create(@RequestBody RevistaCientificaInputDTO revistaCientificaInputDTO, UriComponentsBuilder uriBuilder){
        RevistaCientificaOutputDTO revistaCientificaOutputDTO = revistaCientificaService.create(revistaCientificaInputDTO);

        if(revistaCientificaOutputDTO != null){
            UriComponents uriComponents = uriBuilder.path("/api/revistacientifica/{id}").buildAndExpand(revistaCientificaOutputDTO.getId());
            URI uri = uriComponents.toUri();

            // Define links HATEOAS para o recurso criado, incluindo opções de listagem e exclusão.
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).getById(revistaCientificaOutputDTO.getId(), uriBuilder)).withSelfRel();
            Link allRevistaLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).list()).withRel("all-revistas");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).delete(revistaCientificaOutputDTO.getId())).withRel("delete-revista");

            EntityModel resource = EntityModel.of(revistaCientificaOutputDTO, selfLink, allRevistaLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint PUT para atualizar uma revista científica existente.
    // Atualiza os dados e retorna a revista atualizada com links HATEOAS.
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<RevistaCientificaOutputDTO>> update(@PathVariable Long id, @RequestBody RevistaCientificaInputDTO revistaCientificaInputDTO, UriComponentsBuilder uriBuilder){
        RevistaCientificaOutputDTO revistaCientificaOutputDTO = revistaCientificaService.update(id, revistaCientificaInputDTO);

        if(revistaCientificaOutputDTO != null){
            UriComponents uriComponents = uriBuilder.path("/api/revistacientifica/{id}").buildAndExpand(revistaCientificaOutputDTO.getId());
            URI uri = uriComponents.toUri();

            // Links HATEOAS para a revista atualizada, listagem e exclusão.
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).getById(
                            revistaCientificaOutputDTO.getId(), uriBuilder)).withSelfRel();
            Link allRevistaLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(RevistaCientificaController.class).list()).withRel("all-revistas");
            Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(
                    RevistaCientificaController.class).delete(revistaCientificaOutputDTO.getId())).withRel("delete-revista");

            EntityModel resource = EntityModel.of(revistaCientificaOutputDTO, selfLink, allRevistaLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint DELETE para remover uma revista científica pelo ID.
    // Retorna NO_CONTENT se a remoção for bem-sucedida ou INTERNAL_SERVER_ERROR se falhar.
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        RevistaCientificaOutputDTO possivelRevista = revistaCientificaService.getById(id);

        if(possivelRevista != null){
            revistaCientificaService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
