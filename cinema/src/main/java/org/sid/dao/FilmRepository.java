package org.sid.dao;

import java.util.List;

import org.sid.entities.Film;
import org.sid.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource
@CrossOrigin("*")
public interface FilmRepository extends JpaRepository<Film, Long> {
	@Query("select b from Film b where b.categorie.name = ?1")
	List<Film> findCategorieByFilm(String name);	
}
