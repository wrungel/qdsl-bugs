package frol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Child {
    @Id @GeneratedValue(strategy = IDENTITY)
    Long id;

    @ManyToOne
    Ereignis ereignis;
}
