package frol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Ereignis {
    private static Logger logger = LoggerFactory.getLogger("TEST");

    @Id @GeneratedValue(strategy = IDENTITY)
    Long id;

    @OneToMany(mappedBy = "ereignis", cascade = ALL)
    List<Child> toStringChildren = new ArrayList<>();

    @OneToMany(mappedBy = "ereignis", cascade = ALL)
    List<Child> otherChildren = new ArrayList<>();

    List<Child> getToStringChildren() {
        return toStringChildren;
    }

    List<Child> getOtherChildren() {
        return otherChildren;
    }

    @Override
    public String toString() {
        logger.debug("*** calling Ereignis.toString() ...");
        return "toStringChildren.size(): " + toStringChildren.size();
    }
}
