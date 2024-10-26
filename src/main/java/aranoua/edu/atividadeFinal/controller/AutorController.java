package aranoua.edu.atividadeFinal.controller;

import aranoua.edu.atividadeFinal.dto.AutorInputDTO;
import aranoua.edu.atividadeFinal.dto.AutorOutputDTO;
import aranoua.edu.atividadeFinal.model.Autor;
import aranoua.edu.atividadeFinal.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/*      Classe de Controle de Autores, responsável por controlar as request e response
    pelo cliente, com os métodos do Mundo Orientado à objeto mapeado de acordo
    com os métodos no HTTP, de forma a montar um CRUD (Create, Read, Update, Delete),
    nesse caso, responsável pelo CRUD de autores.
*/
@RestController
@RequestMapping("/api/autor")
public class AutorController {

    // Injeção do serviço de Autor, que contém a lógica de negócios relacionada à entidade Autor
    @Autowired
    private AutorService autorService;

    /*  Método que faz cadastro de um autor com os dados vindo no corpo da request, em formato de JSON
    Mepeado como um Post, consumindo e produzindo JSON.
    Como parametros tem: um DTO de autor com os dados vindo do JSON consumido e um objeto do tipo UriComponentsBuilder,
    que fica responsável por prover dados da URI, utilizado na construção do HATEOAS
    */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AutorOutputDTO>> create(@RequestBody AutorInputDTO autorInputDTO, UriComponentsBuilder uriBuilder) {

        // Chama o serviço para criar o autor
        AutorOutputDTO autorSalvo = autorService.create(autorInputDTO);

        if(autorSalvo != null){
            // Constrói a URI do recurso criado
            UriComponents uriComponents = uriBuilder.path("/api/autor/{id}").buildAndExpand(autorSalvo.getId());
            URI uri = uriComponents.toUri();

            // Adiciona links de navegação HATEOAS para o recurso criado
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).getById(autorSalvo.getId(), uriBuilder)
            ).withSelfRel();
            Link allAutorLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).list()
            ).withRel("all-autor");
            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).delete(autorSalvo.getId())
            ).withRel("delete-autor");

            // Retorna o recurso criado com os links
            EntityModel<AutorOutputDTO> resource = EntityModel.of(autorSalvo, selfLink, allAutorLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            // Retorna erro interno caso o autor não tenha sido salvo
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*Método que faz uma pesquisa de um dos autores disponiveis.
      Mepeado como um GET com parametro na URL, produzindo Um JSON
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AutorOutputDTO>> list() {
        List<AutorOutputDTO> autores = autorService.list();

        // Retorna a lista de autores se houverem registros ou 404 se estiver vazia
        if(!autores.isEmpty()){
            return new ResponseEntity<>(autores, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para buscar um autor pelo seu ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AutorOutputDTO>> getById(@PathVariable Long id, UriComponentsBuilder uriBuilder) {
        AutorOutputDTO autor = autorService.getById(id);

        if(autor != null){
            UriComponents uriComponents = uriBuilder.path("/api/autor/{id}").buildAndExpand(autor.getId());
            URI uri = uriComponents.toUri();

            // Adiciona links HATEOAS para o autor encontrado
            Link allAutorLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).list()
            ).withRel("all-autor");
            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).delete(autor.getId())
            ).withRel("delete-autor");

            // Retorna o autor com os links HATEOAS
            EntityModel<AutorOutputDTO> resource = EntityModel.of(autor, allAutorLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            // Retorna 404 caso o autor não seja encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para atualizar um autor pelo seu ID
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AutorOutputDTO>> update(@PathVariable Long id, @RequestBody AutorInputDTO autorInputDTO,
                                                              UriComponentsBuilder uriBuilder) {
        AutorOutputDTO autorAlterado = autorService.update(id, autorInputDTO);

        if(autorAlterado != null){
            UriComponents uriComponents = uriBuilder.path("/api/autor/{id}").buildAndExpand(autorAlterado.getId());
            URI uri = uriComponents.toUri();

            // Adiciona links HATEOAS para o autor atualizado
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).getById(autorAlterado.getId(), uriBuilder)
            ).withSelfRel();
            Link allAutorLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).list()
            ).withRel("all-autor");
            Link deleteLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AutorController.class).delete(autorAlterado.getId())
            ).withRel("delete-autor");

            // Retorna o autor atualizado com os links HATEOAS
            EntityModel<AutorOutputDTO> resource = EntityModel.of(autorAlterado, selfLink, allAutorLink, deleteLink);
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);

        } else {
            // Retorna 404 caso o autor a ser atualizado não seja encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para excluir um autor pelo seu ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        AutorOutputDTO autor = autorService.getById(id);

        if (autor != null) {
            Boolean possivelDelete = autorService.delete(autor.getId());
            if (possivelDelete) {
                // Retorna status 204 caso a exclusão seja bem-sucedida
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                // Retorna erro interno se não for possível excluir o autor
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // Retorna 404 caso o autor não seja encontrado
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
