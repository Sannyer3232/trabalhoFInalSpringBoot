package aranoua.edu.atividadeFinal.controller;

import aranoua.edu.atividadeFinal.dto.AfiliacaoInputDTO;
import aranoua.edu.atividadeFinal.dto.AfiliacaoOutputDTO;
import aranoua.edu.atividadeFinal.service.AfiliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

//Classe Controller da Afiliacao
@RestController
@RequestMapping("/api/afiliacao")
public class AfiliacaoController {

    @Autowired
    private AfiliacaoService afiliacaoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AfiliacaoOutputDTO>> list(){

        List<AfiliacaoOutputDTO> afiliacoes = afiliacaoService.list();

        if(!afiliacoes.isEmpty()){
            return new ResponseEntity<>(afiliacoes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntityModel<AfiliacaoOutputDTO>> create(@RequestBody AfiliacaoInputDTO afiliacaoInputDTO, UriComponentsBuilder uriBuilder) {

        AfiliacaoOutputDTO afiliacaoOutputDTO = afiliacaoService.create(afiliacaoInputDTO);

        if (afiliacaoOutputDTO != null) {

            UriComponents uriComponents = uriBuilder.path("/api/afiliacao/{id}").buildAndExpand(afiliacaoOutputDTO.getId());

            URI uri = uriComponents.toUri();

            Link selflink = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(AfiliacaoController.class).list()).withSelfRel();
            EntityModel resource = EntityModel.of(afiliacaoOutputDTO, selflink);

            return ResponseEntity.created(uri).contentType(MediaType.APPLICATION_JSON).body(resource);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
