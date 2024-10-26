package aranoua.edu.atividadeFinal.controller;

import aranoua.edu.atividadeFinal.dto.ArtigoInputDTO;
import aranoua.edu.atividadeFinal.dto.ArtigoOuputDTO;
import aranoua.edu.atividadeFinal.model.Artigo;
import aranoua.edu.atividadeFinal.service.ArtigoService;
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

import javax.swing.text.html.parser.Entity;
import java.net.URI;
import java.util.List;

/*      Classe de Controle de Artigos, responsável por controlar as request e response
    pelo cliente, com os métodos do Mundo Orientado à objeto mapeado de acordo
    com os métodos no HTTP, de forma a montar um CRUD (Create, Read, Update, Delete),
    nesse caso, responsável pelo CRUD de artigos.
*/

@RestController
@RequestMapping("/api/artigo")
public class ArtigoController {
    //Injeção da dependência do Classe de Serviço do Artigo
    @Autowired
    private ArtigoService artigoService;

    /*Método que faz uma listagem de todos os artigos disponiveis.
      Mepeado como um GET sem parametro na URL, produzindo um JSON
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArtigoOuputDTO>> list() {


        //É solitado pela camada de Serviço uma listagem dos artigos
        List<ArtigoOuputDTO> artigoOuputDTOS = artigoService.list();


        //Verifcação se a lista retornada contém artigos.
        if (!artigoOuputDTOS.isEmpty()) {
            //Se houver artigos, a lista é enviada no corpo da response como um JSON e com Status ok
            return new ResponseEntity<>(artigoOuputDTOS, HttpStatus.OK);
        }else{
            //Caso vazia, é enviado apenas o Status Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*Método que faz uma pesquisa de um dos artigos disponiveis.
      Mepeado como um GET com parametro na URL, produzindo Um JSON
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtigoOuputDTO> getById(@PathVariable Long id) {

        //É solitado pela camada de Serviço uma pesquisa de um artigo através do Id do artigo
        ArtigoOuputDTO artigoOuputDTO = artigoService.getById(id);

        if (artigoOuputDTO != null) {
            //Se houver artigo, o artigo é enviado no corpo da response como um JSON e com Status ok
            return new ResponseEntity<>(artigoOuputDTO, HttpStatus.OK);
        }else{
            //Caso vazia, é enviado apenas o Status Not Found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*  Método que faz cadastro de um artigo com os dados vindo no corpo da request, em formato de JSON
        Mepeado como um Post, consumindo e produzindo JSON.
        Como parametros tem: um DTO de artigo com os dados vindo do JSON consumido e um objeto do tipo UriComponentsBuilder,
        que fica responsável por prover dados da URI, utilizado na construção do HATEOAS
    */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ArtigoOuputDTO>> create(@RequestBody ArtigoInputDTO artigoInputDTO, UriComponentsBuilder uriBuilder) {

        //Solicitado à camada de Serviço que salve o artigo
        ArtigoOuputDTO artigoOuputDTO = artigoService.create(artigoInputDTO);

        //Verificação se o artigo foi salvo com sucesso
        if (artigoOuputDTO != null) {
            /*
                Caso o artigo tenha sido salvo com sucesso, inicia o processo da construção do HATEOAS.
                Iniciado pelo construção do UriComponets, através do uriBuilder, vai criar um componente da URI para os métodos
            */
            UriComponents uriComponents = uriBuilder.path("/api/artigo/{id}").buildAndExpand(artigoOuputDTO.getId());

            //Através da uriComponents, a URI é criada
            URI uri = uriComponents.toUri();

            /*
            * Iniciado a construção dos Links que fazem requisição dos métodos mapeados,
            * começando pela pesquisa
            * */
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).getById(artigoOuputDTO.getId())
            ).withSelfRel();
            //Link do método de listagem
            Link allArtigo = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).list()
            ).withRel("all-Artigo");
            //Link do método de delete
            Link deleteArtigo = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).delete(artigoOuputDTO.getId())
            ).withRel("delete-artigo");

            /*
            * Criação de EntityModel, que vai receber o artigo que foi criado e os links criados
            * */
            EntityModel resource = EntityModel.of(artigoOuputDTO, selfLink, allArtigo, deleteArtigo);
            /*
            * O artigo é enviado no corpo da response como um JSON, junto com os links HATEOS e com Status ok
            * */
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        }else{
            //Em caso de algum erro, retorna Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*  Método que faz a update de um artigo com os dados vindo no corpo da request, em formato de JSON
        Mepeado como um Put, consumindo e produzindo JSON.
        Como parametros tem: id do artigo para ser alterado, um DTO de artigo com os dados vindo do JSON consumido
        e um objeto do tipo UriComponentsBuilder,
        que fica responsável por prover dados da URI, utilizado na construção do HATEOAS
    */
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<ArtigoOuputDTO>> create(@PathVariable Long id ,@RequestBody ArtigoInputDTO artigoInputDTO, UriComponentsBuilder uriBuilder) {

        //Solicitado à camada de Serviço que altere e salve o artigo
        ArtigoOuputDTO artigoOuputDTO = artigoService.update(id, artigoInputDTO);

        //Verificação se o artigo foi salvo com sucesso
        if (artigoOuputDTO != null) {

             /*
                Caso o artigo tenha sido salvo com sucesso, inicia o processo da construção do HATEOAS.
                Iniciado pelo construção do UriComponets, através do uriBuilder, vai criar um componente da URI para os métodos
            */
            UriComponents uriComponents = uriBuilder.path("/api/artigo/{id}").buildAndExpand(artigoOuputDTO.getId());

            //Através da uriComponents, a URI é criada
            URI uri = uriComponents.toUri();

            /*
             * Iniciado a construção dos Links que fazem requisição dos métodos mapeados,
             * começando pela pesquisa
             * */
            Link selfLink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).getById(artigoOuputDTO.getId())
            ).withSelfRel();

            //Link do método de listagem
            Link allArtigo = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).list()
            ).withRel("all-Artigo");

            //Link do método de delete
            Link deleteArtigo = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(ArtigoController.class).delete(artigoOuputDTO.getId())
            ).withRel("delete-artigo");

            /*
             * Criação de EntityModel, que vai receber o artigo que foi criado e os links criados
             * */
            EntityModel resource = EntityModel.of(artigoOuputDTO, selfLink, allArtigo, deleteArtigo);

            /*
             * O artigo é enviado no corpo da response como um JSON, junto com os links HATEOS e com Status ok
             * */
            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        }else{
            //Em caso de algum erro, retorna Internal Server Error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*  Método que faz delete de um artigo com os id vindo pela URI
        Mepeado como um Delete, consumindo e produzindo JSON.
        Como parametros tem: id do artigo para ser alterado.
    */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        //Solicitado a camada de Serivo, o artigo que vai ser deletado
        ArtigoOuputDTO artigoOuputDTO = artigoService.getById(id);
        if (artigoOuputDTO != null) {
            //Caso o artigo exista, solicitado pela a deleção pela camada de Serviço
            artigoService.delete(artigoOuputDTO.getId());
            //Resposta do sucesso da deleção
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
