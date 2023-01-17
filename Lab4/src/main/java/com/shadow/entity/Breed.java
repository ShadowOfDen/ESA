package com.shadow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(schema = "public", name = "breed")
public class Breed {
    @Id
    @Column(name = "id", nullable = false,unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "breed_name", length = 100)
    private String breedName;
    @OneToMany(mappedBy = "breed")
    @JsonIgnore
    private List<Cat> cats;

    public Breed() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }


    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Breed breed = (Breed) o;
        return id == breed.id &&
                Objects.equals(breedName, breed.breedName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, breedName);
    }
}

