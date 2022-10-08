package dev.moreno.recipe_project.domains;

import ch.qos.logback.core.util.DefaultInvocationGate;
import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Note note;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Long prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public Long getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(Long cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    public Short getServingPeople() {
        return servingPeople;
    }

    public void setServingPeople(Short servingPeople) {
        this.servingPeople = servingPeople;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        ingredients.forEach(i->i.setRecipe(this));
    }

    public Note getNote() {
        return note;
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
}
