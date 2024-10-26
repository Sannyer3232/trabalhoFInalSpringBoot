package aranoua.edu.atividadeFinal.repository;

import aranoua.edu.atividadeFinal.model.RevistaCientifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Interface de Persistencia (Repository) das Revistas Cietificas
@Repository
public interface RevistaCientificaRespository extends JpaRepository<RevistaCientifica,Long> {

    //Método abstrato de retorna uma revista pesquisada pelo nome
    @Query("select r from tabRevistaCientifica r where r.nome = :nome")
    RevistaCientifica findByNome(@Param("nome") String nome);
}
