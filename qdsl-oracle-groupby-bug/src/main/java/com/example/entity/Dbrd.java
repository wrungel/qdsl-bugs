package com.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Dbrd {
    @Id
    @SequenceGenerator(name = "S_DBRD", allocationSize = 1, sequenceName = "S_DBRD")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_DBRD")        
    private Long id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "DBRD_DBRE_ID")    
    private Dbre dbre;
    
    private String name;
    
    private Long value;
    
    
    
    public Long getId() {
        return id;
    }

    public Dbre getDbre() {
        return dbre;
    }

    public void setDbre(Dbre dbre) {
        this.dbre = dbre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
    
    
    
}
