package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class CartelDeNachosImpl implements CartelDeNachos {
    private Map<Integer, CasaDeBurrito> restruants;
    private Map<Integer, Profesor> profesors;
    private Map<Profesor, Set<Profesor>> profesors_graph;

    public CartelDeNachosImpl() {
        this.restruants = new HashMap<Integer, CasaDeBurrito>();
        this.profesors = new HashMap<Integer, Profesor>();
        this.profesors_graph = new HashMap<Profesor, Set<Profesor>>();
    }

    public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException {
        if (profesors.containsKey(id)) {
            throw new Profesor.ProfesorAlreadyInSystemException();
        }
        Profesor new_profesor = new ProfesorImpl(id, name);
        profesors_graph.put(new_profesor, new HashSet<Profesor>());
        profesors.put(id, new_profesor);
        return new_profesor;
    }

    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException {
        if (restruants.containsKey(id)) {
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        }
        CasaDeBurrito new_casa = new CasaDeBurritoImpl(id, name, dist, menu);
        restruants.put(id, new_casa);
        return new_casa;
    }


    public Collection<Profesor> registeredProfesores() {
        return new HashSet<Profesor>(this.profesors.values());
    }


    public Collection<CasaDeBurrito> registeredCasasDeBurrito() {
        return new HashSet<CasaDeBurrito>(this.restruants.values());
    }

    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException {

        if (!profesors.containsKey(id)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        return profesors.get(id);
    }


    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException {
        if (!restruants.containsKey(id)) {
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        return restruants.get(id);
    }


    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException {
        if (!profesors_graph.containsKey(p1) || !profesors_graph.containsKey(p2)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (p1.equals(p2)) {
            throw new Profesor.SameProfesorException();
        }
        if (profesors_graph.get(p1).contains(p2)) {
            throw new Profesor.ConnectionAlreadyExistsException();
        }
        profesors_graph.get(p1).add(p2);
        profesors_graph.get(p2).add(p1);
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;
    }


    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException {
        if (!profesors_graph.containsKey(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        List<CasaDeBurrito> favorites = new LinkedList<>();
        for (Profesor friend : profesors_graph.get(p).stream().sorted().toList()) {
            favorites.addAll(
                    friend.favoritesByRating(0).stream()
                            .filter(x -> !favorites.contains(x)).toList()
            );
        }
        return favorites;
    }


    public Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException {
        if (!profesors_graph.containsKey(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        List<CasaDeBurrito> favorites = new LinkedList<>();
        for (Profesor friend : profesors_graph.get(p).stream().sorted().toList()) {
            favorites.addAll(
                    friend.favoritesByDist(Integer.MAX_VALUE).stream()
                            .filter(x -> !favorites.contains(x)).toList()
            );
        }
        return favorites;
    }


    public boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, ImpossibleConnectionException {
        if (!profesors_graph.containsKey(p)) {
            throw new Profesor.ProfesorNotInSystemException();
        }
        if (!restruants.containsKey(c.getId())) {
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        }
        if (t < 0) {
            throw new ImpossibleConnectionException();
        }
        if (p.favorites().contains(c))
        {
            return true;
        }
        if (t == 0) {
            return false;
        }
        for (Profesor friend : profesors_graph.get(p)) {
            if (friend.favorites().contains(c) || getRecommendation(friend, c, t - 1)) {
                return true;
            }
        }
        return false;
    }


    public List<Integer> getMostPopularRestaurantsIds() {
        Map<Integer, Integer> restruants_ratings = new HashMap<>();
        for (Set<Profesor> friends : profesors_graph.values()) {
            for (Profesor p : friends) {
                for (CasaDeBurrito c : p.favorites()) {
                    restruants_ratings.putIfAbsent(c.getId(), 1);
                    restruants_ratings.put(c.getId(), restruants_ratings.get(c.getId()) + 1);
                }
            }
        }
        int max = 0;
        for (int value : restruants_ratings.values()) {
            if (value >= max) {
                max = value;
            }
        }
        int finalMax = max;
        return restruants_ratings.entrySet()
                .stream()
                .filter(x -> x.getValue() == finalMax)
                .map(Map.Entry::getKey)
                .sorted()
                .toList();
    }
    public String toString() {
        String str = "";
        str += "Registered profesores: " + this.profesors.keySet().stream()
                .sorted()
                .toList()
                .toString()
                .substring(1, this.profesors.keySet().toString().length() - 1) + ".\n";
        str += "Registered casas de burrito: " + this.restruants.keySet().stream()
                .sorted()
                .toList()
                .toString()
                .substring(1, this.restruants.keySet().toString().length() - 1) + ".\n";
        str += "Profesores:\n";
        for (Profesor profesor : profesors_graph.keySet()) {
            str += profesor.getId() + " -> " + profesors_graph.get(profesor)
                    .stream()
                    .map(x->x.getId())
                    .sorted()
                    .toList() + ".\n";
        }
        str += "End profesores.";
        return str;
    }
}
