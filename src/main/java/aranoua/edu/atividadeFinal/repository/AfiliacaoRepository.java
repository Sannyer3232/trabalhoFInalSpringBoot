package aranoua.edu.atividadeFinal.repository;

import aranoua.edu.atividadeFinal.model.Afiliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Camada de persitencia da Afiliação
@Repository
public interface AfiliacaoRepository extends JpaRepository<Afiliacao, Long> {

    @Query("select af from tabafiliacao af where af.sigla = :sigla")
    Afiliacao findBySilga(@Param("sigla")String nome);
}
