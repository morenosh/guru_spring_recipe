package dev.moreno.recipe_project.domains;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe extends BaseEntity<Long> {
    private String title;
    private Long prepTimeMinutes;
    private Long cookTimeMinutes;
    private Short servingPeople;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    @Lob
    private Byte[] image;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @ToString.Exclude
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    @ToString.Exclude
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        ingredients.forEach(i->i.setRecipe(this));
    }

    public void setNote(Note note) {
        if (note == this.note) return;

        this.note = note;
        note.setRecipe(this);
    }

    public Recipe addIngredient(Ingredient ingredient) {
        var exist = !ingredients.add(ingredient);
        if (exist) return this;

        ingredient.setRecipe(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
