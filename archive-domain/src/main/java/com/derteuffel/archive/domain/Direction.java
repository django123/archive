package com.derteuffel.archive.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "direction")
public class Direction extends AbstractPersistence{


    private String region;
    private String name;
    private String ville;
    private String location;

    public Direction() {
    }

    public Direction(String region, String name, String ville, String location) {
        this.region = region;
        this.name = name;
        this.ville = ville;
        this.location = location;
    }



    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
}
