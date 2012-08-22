package com.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dbre {
    
    @Id
    @SequenceGenerator(name = "S_DBRE", allocationSize = 1, sequenceName = "S_DBRE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DBRE")    
    private Long id;
    
    private String name;
    
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dbre", fetch = FetchType.LAZY, orphanRemoval = true)    
    private List<Dbrd> dbrds = new ArrayList<Dbrd>();
    
    public Long getId() {
        return id;
    }
    



    public List<Dbrd> getDbrds() {
        return dbrds;
    }




    public String getName() {
        return name;
    }




    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }




    public String getDescription() {
        return description;
    }




    public void setDescription(String description) {
        this.description = description;
    }
    
}
