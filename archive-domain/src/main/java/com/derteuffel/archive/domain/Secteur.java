package com.derteuffel.archive.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "secteur")
public class Secteur extends AbstractPersistence{



    private String name;
    private String ville;
    private String location;

    @ManyToOne
    private Direction direction;

    public Secteur() {
    }

    public Secteur(String name, String ville, String location) {
        this.name = name;
        this.ville = ville;
        this.location = location;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
