package com.wrungel.example;

import javax.persistence.*;
import java.io.Serializable;


@Entity
public class Baa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private TestEinheit einheit;

    public Baa(TestEinheit einheit) {
        this.einheit = einheit;
    }

    public Long getId() {
        return id;
    }
}
