package dev.moreno.recipe_project.repositories;

import dev.moreno.recipe_project.domains.Notes;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Notes, Long> {
}
