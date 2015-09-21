package frol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Ereignis ereignis;


    public Long getId() {
        return id;
    }

    public Ereignis getEreignis() {
        return ereignis;
    }

    public void setEreignis(Ereignis ereignis) {
        this.ereignis = ereignis;
    }
}
