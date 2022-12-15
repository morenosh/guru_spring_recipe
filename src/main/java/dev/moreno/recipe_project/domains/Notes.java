package dev.moreno.recipe_project.domains;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Data
public class Notes extends BaseEntity<Long>{
    private String notes;

    @OneToOne
    private Recipe recipe;

}
