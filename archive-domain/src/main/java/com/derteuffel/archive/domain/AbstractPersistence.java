package com.derteuffel.archive.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public  class AbstractPersistence implements Serializable {


    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name="created_at")
    @JsonProperty(value = "createdAt")
    Date createdAt;

    @Temporal(TemporalType.DATE)
    @Column(name="updated_at")
    @JsonProperty(value = "updatedAt")
    Date updatedAt;

    @Temporal(TemporalType.DATE)
    @Column(name="deleted_at")
    @JsonProperty(value = "deletedAt")
    Date deletedAt;

    @Column(name = "deleted")
    @JsonProperty(value = "isDeleted")
    Boolean isDeleted = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
        updatedAt = new Date();
        isDeleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = new Date();
    }

    @PreRemove
    public void preRemove() {
        deletedAt = new Date();
        isDeleted = true;
    }
}
