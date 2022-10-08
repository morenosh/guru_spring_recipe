package dev.moreno.recipe_project.domains;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Category extends BaseEntity<Short> {

    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

    public String getDescription() {
        return description;
    }

    public void setDescription(String departmentName) {
        this.description = departmentName;
    }
}
