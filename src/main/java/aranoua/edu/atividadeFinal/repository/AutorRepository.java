package aranoua.edu.atividadeFinal.repository;

import aranoua.edu.atividadeFinal.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//Interface de Persistencia (Repository) dos Autores
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    //Método abstrato de retorna um autor pelo nome
    @Query("select a from tabAutor a where a.nome = :nome")
    Autor findByNome(@Param("nome") String nome);
}
