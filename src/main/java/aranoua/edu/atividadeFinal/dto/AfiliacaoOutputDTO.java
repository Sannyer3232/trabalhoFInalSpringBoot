package aranoua.edu.atividadeFinal.dto;

import aranoua.edu.atividadeFinal.model.Afiliacao;
import aranoua.edu.atividadeFinal.model.Autor;

import java.util.ArrayList;
import java.util.List;

//Classe para ajustes de saidos dos dados de Alfiliação
public class AfiliacaoOutputDTO {

    private Long id;
    private String nome;
    private String sigla;
    private String referencia;
    private List<String> autores = new ArrayList<>();


    public AfiliacaoOutputDTO() {
    }

    //Método Construtor de AfiliacaoOutoutDTO a partir do objeto de Afiliacao
    public AfiliacaoOutputDTO(Afiliacao afiliacao) {
        this.id = afiliacao.getId();
        this.nome = afiliacao.getNome();
        this.sigla = afiliacao.getSigla();
        this.referencia = afiliacao.getReferencia();
        List<String> nomeAutores = new ArrayList<>();
        for (Autor autor : afiliacao.getAutores()) {
            nomeAutores.add(autor.getNome());
        }
        this.autores = nomeAutores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<String> getAutores() {
        return autores;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }
}
