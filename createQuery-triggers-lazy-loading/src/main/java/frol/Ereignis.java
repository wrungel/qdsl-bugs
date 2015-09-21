package frol;

import org.hibernate.annotations.Immutable;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Immutable
public class Ereignis {

    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "ereignis", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            //name = "T_ERGS_EREIGNISINFORMATION",
            joinColumns = @JoinColumn(
                    //name = "ERIN_ERGS_ID",
                    //referencedColumnName = "ERGS_ID",
                    nullable = false
            ))
    @MapKeyColumn(name = "ERIN_SCHLUESSEL")
    private Map<String, String> kontext = new HashMap<>();


    public List<Child> getChildren() {
        return children;
    }

    public Long getId() {
        return id;
    }

    public Map<String, String> getKontext() {
        return kontext;
    }


    @Override
    public String toString() {
        return "kontext: " + kontext;
    }
}
