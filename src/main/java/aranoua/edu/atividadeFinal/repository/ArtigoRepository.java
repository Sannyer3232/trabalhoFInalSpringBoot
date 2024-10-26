package aranoua.edu.atividadeFinal.repository;

import aranoua.edu.atividadeFinal.model.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
//Interface de Persistencia (Repository) dos Artigos
@Repository
public interface ArtigoRepository extends JpaRepository<Artigo, Long> {

}
