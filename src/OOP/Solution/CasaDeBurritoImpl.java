package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import org.w3c.dom.ranges.Range;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CasaDeBurritoImpl implements CasaDeBurrito {

    private int id;
    private String name;
    private int dist;
    private Set<String> menu;
    private Map<Profesor,Integer> ratingMap;

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu) {
        this.id = id;
        this.name = name;
        this.dist = dist;
        this.menu = new HashSet<String>(menu);
        ratingMap = new HashMap<Profesor,Integer>();
    }
    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public int distance(){
        return this.dist;
    }

    public boolean isRatedBy(Profesor p){
        return this.ratingMap.containsKey(p);
    }

    public CasaDeBurrito rate(Profesor p, int r)
            throws CasaDeBurrito.RateRangeException {
        if(r < 0 || r> 5)
        {
            throw new CasaDeBurrito.RateRangeException();
        }
        this.ratingMap.put(p,r);
        return this;
    }

    public int numberOfRates() {
        return this.ratingMap.size();
    }

    public double averageRating() {
        int sum = 0;
        if(this.ratingMap.size() == 0)
        {
            return 0;
        }
        for (Integer value : this.ratingMap.values()) {
            sum += value;
        }
        return ((double)sum) / this.ratingMap.size();
    }

    public String toString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o.getClass() != this.getClass())
            return false;
        return ((CasaDeBurrito)o).getId() == this.getId();
    }

    @Override
    public int compareTo(CasaDeBurrito other) {
        return this.getId()-other.getId();
    }

}
