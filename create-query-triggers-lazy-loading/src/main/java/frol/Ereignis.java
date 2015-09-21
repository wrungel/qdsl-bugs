package frol;

import javax.persistence.CascadeType;
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

    @OneToMany(mappedBy = "ereignis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> toStringChildren = new ArrayList<>();

    @OneToMany(mappedBy = "ereignis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> otherChildren = new ArrayList<>();

    public List<Child> getToStringChildren() {
        return toStringChildren;
    }

    public List<Child> getOtherChildren() {
        return otherChildren;
    }

    @Override
    public String toString() {
        return "toStringChildren.size(): " + toStringChildren.size();
    }
}
