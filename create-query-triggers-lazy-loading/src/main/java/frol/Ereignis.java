package frol;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ereignis {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "ereignis", orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    public List<Child> getChildren() {
        return children;
    }

    public Long getId() {
        return id;
    }
}
