package aranoua.edu.atividadeFinal.dto;

import aranoua.edu.atividadeFinal.model.Afiliacao;
import jakarta.persistence.Column;
//Classe de ajuste de dados para inserção
public class AfiliacaoInputDTO {

    private String nome;
    private String sigla;
    private String referencia;

    //Métodos Construtores


    public AfiliacaoInputDTO() {
    }

    public AfiliacaoInputDTO(String nome, String sigla, String referencia) {
        this.nome = nome;
        this.sigla = sigla;
        this.referencia = referencia;
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

    //Método para Transformar AfiliaçãoInputDTO para Afiliacao
    public Afiliacao build() {
        Afiliacao afiliacao = new Afiliacao();
        afiliacao.setNome(this.nome);
        afiliacao.setSigla(this.sigla);
        afiliacao.setReferencia(this.referencia);
        return afiliacao;
    }
}
