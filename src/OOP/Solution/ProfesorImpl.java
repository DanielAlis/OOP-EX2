package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor{
    private int id;
    private String name;
    private Set<Profesor> friends;
    private Set<CasaDeBurrito> favorites;

    public ProfesorImpl(int id, String name) {
        this.id = id;
        this.name = name;
        this.friends = new HashSet<Profesor>();
        this.favorites = new HashSet<CasaDeBurrito>();
    }
    public int getId() {
        return this.id;
    }


    public Profesor favorite(CasaDeBurrito c)
            throws Profesor.UnratedFavoriteCasaDeBurritoException {
        if (!c.isRatedBy(this))
        {
            throw new Profesor.UnratedFavoriteCasaDeBurritoException();
        }
        this.favorites.add(c);
        return this;
    }

    public Collection<CasaDeBurrito> favorites() {
        return new HashSet<CasaDeBurrito>(this.favorites);
    }


    public Profesor addFriend(Profesor p)
            throws Profesor.SameProfesorException, Profesor.ConnectionAlreadyExistsException {
        if (this.equals(p))
        {
            throw new SameProfesorException();
        }
        if (this.friends.contains(p))
        {
            throw new ConnectionAlreadyExistsException();
        }
        this.friends.add(p);
        return this;
    }


    public Set<Profesor> getFriends() {
        return new HashSet<Profesor>(this.friends);
    }


    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        Set<Profesor> friends_cpy = new HashSet<Profesor>(this.friends);
        return friends_cpy.stream()
                .filter(p).
                collect(Collectors.toSet());
    }


    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        Set<CasaDeBurrito> favorites_cpy = new HashSet<>(favorites);
        return favorites_cpy.stream()
                .filter(p)
                .sorted(comp)
                .collect(Collectors.toList());
    }


    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return filterAndSortFavorites(
                ((x,y) -> {
                                    int diff = (int)(y.averageRating() - x.averageRating());
                                    if (diff !=0) {
                                        return diff;
                                    }
                                    diff = x.distance() - y.distance();
                                    if (diff != 0)
                                    {
                                        return diff;
                                    }
                                    else{
                                        return (x.getId()-y.getId());
                                    }
                })
        ,(rate -> rate.averageRating() >= rLimit));
    }


    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return filterAndSortFavorites(
                ((c1,c2)-> {
                    int diff = c1.distance() - c2.distance();
                    if(diff != 0) {
                        return diff;
                    }
                    diff = (int) (c2.averageRating() - c1.averageRating());
                    if(diff != 0) {
                        return diff;
                    }
                    return c1.getId() - c2.getId();
                }),
                (x -> x.distance() <= dLimit)
                );
    }



    public String toString() {
return "";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
        {
            return false;
        }
        if(obj.getClass() != this.getClass())
            return false;
        return this.getId() == ((Profesor)obj).getId();
    }

    @Override
    public int compareTo(Profesor other) {
        return this.getId()-other.getId();
    }
}
