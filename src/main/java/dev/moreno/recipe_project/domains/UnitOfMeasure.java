package dev.moreno.recipe_project.domains;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UnitOfMeasure extends BaseEntity<Short> {

    private String uom;

    public String getUom() {
        return uom;
    }
    public void setUom(String uom) {
        this.uom = uom;
    }
}
