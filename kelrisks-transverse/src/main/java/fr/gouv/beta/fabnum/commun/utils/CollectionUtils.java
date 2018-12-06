package fr.gouv.beta.fabnum.commun.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {
    
    /**
     * Transforme un simple Iterable en une liste en eliminant les doublons
     * se base sur le equals donc la clé fonctionnelle pour les entités ...
     */
    static public <T> List<T> toList(Iterable<T> iterable) {
        
        Set<T> set = new LinkedHashSet<>();
        for (T obj : iterable) {
            set.add(obj);
        }
        
        return new ArrayList<>(set);
    }
    
    /**
     * Vérifie si la collection est vide, en faisant attention au cas où elle est null
     *
     * @param collection
     *
     * @return true si la collection est null ou vide
     */
    static public boolean isEmpty(Collection<?> collection) {
        
        return collection == null || collection.isEmpty();
    }
    
    
    /**
     * Vérifie si la collection n'est pas vide, en faisant attention au cas où elle est null
     *
     * @param collection
     *
     * @return false si la collection est null ou vide
     */
    static public boolean isNotEmpty(Collection<?> collection) {
        
        return !isEmpty(collection);
    }
    
    
    /**
     * Remplace la collection de type Set d'un objet par une autre. Teste si des items sont identiques
     *
     * @return true si la collection est null ou vide
     */
    static public <T> Set<T> remplaceCollection(Set<T> objetGetListe, Set<T> ancienneCollection, Set<T> nouvelleCollection) {
        
        
        for (T obj : nouvelleCollection) {
            if (!ancienneCollection.contains(obj)) {
                objetGetListe.add(obj);
                //System.out.println("LISTE emplacement ajout " + obj.hashCode());
            }
        }
        
        
        for (T obj : ancienneCollection) {
            if (!nouvelleCollection.contains(obj)) {
                objetGetListe.remove(obj);
                // System.out.println("LISTE emplacement suppression " + obj.hashCode());
            }
        }
        
        return objetGetListe;
    }
    
    //        Hashtable<Integer, T> source1 = new Hashtable<Integer,T>();
    //        HashMap<Integer, T>  map1 = new HashMap(source1);
    //        for(T objav : ancienneCollection) map1.put(Integer.valueOf(objav.hashCode()), objav);
    //
    //
    //        Hashtable<Integer, T> source2 = new Hashtable<Integer,T>();
    //        HashMap<Integer, T>  map2 = new HashMap(source2);
    //        for(T objap : nouvelleCollection) map2.put(Integer.valueOf(objap.hashCode()), objap);
    //
    //        for (Map.Entry<Integer, T> entry : map1.entrySet()) {
    //            if (!map2.containsValue(entry.getValue())) {
    //                T obj = entry.getValue();
    //                objetGetListe.remove(obj);
    //            }
    //        }
    //
    //        for (Map.Entry<Integer, T> entry : map2.entrySet()) {
    //            if (!map1.containsValue(entry.getValue())) {
    //                T obj = entry.getValue();
    //                objetGetListe.add(obj);
    //            }
    //        }
    
    
    //
    //        if (!map1.containsKey(map2)) objetGetListe.add(map2.)
    //
    //       List <Integer> newhash = new ArrayList<Integer>();
    //       for(T objap : nouvelleCollection) anchash.add(objap.hashCode());
    //
    //
    //       for (Integer hashcod : newhash) {
    //           if (!anchash.contains(hashcod)
    //       }
    //
    //        for(T obj : nouvelleCollection) {
    //             if (!ancienneCollection.contains(obj)) { objetGetListe.add(obj); System.out.println("LISTE emplacement ajout " + obj.hashCode());}
    //        }
    //
    //        for(T objav4 : objetGetListe) System.out.println("LISTE emplacement apres ajout 1 : " +  objav4.hashCode());
    //
    //
    //        for(T obj : ancienneCollection) {
    //             if (!nouvelleCollection.contains(obj)) { objetGetListe.remove(obj); System.out.println("LISTE emplacement suppression " + obj.hashCode());}
    //        }
    //
    //
    //        for(T objav5 : objetGetListe) System.out.println("LISTE emplacement apres suppression 1 : " +  objav5.hashCode());
    
    
    //        Set<T> listeToRemove = new HashSet<T>();
    //
    //        Iterator<T> ogl = objetGetListe.iterator();
    //        while (ogl.hasNext()) {
    //            T monObjet = ogl.next();
    //            if (!nouvelleCollection.contains(monObjet)) System.out.println("suppression " + monObjet.hashCode()); listeToRemove.add(monObjet);
    //        }
    //
    //        if (listeToRemove != null) objetGetListe.removeAll(listeToRemove);
    
    
    //        for(T obj : ancienneCollection) {
    //             if (!nouvelleCollection.contains(obj)) {
    //                 for(T ogl : objetGetListe) if (ogl.hashCode() == obj.hashCode()) objetGetListe.remove(ogl); System.out.println("suppression " + obj.hashCode());
    //             }
    //        }
    
    
    /**
     * Remplace la collection de type Set d'un objet par une autre. Teste si des items sont identiques
     *
     * @return true si la collection est null ou vide
     */
    static public <T> Set<Set<T>> remplaceCollections(Set<T> ancienneCollection, Set<T> nouvelleCollection) {
        
        Set<T> listeToRemove = ancienneCollection;
        Set<T> listeToAdd    = nouvelleCollection;
        
        
        for (T obj : nouvelleCollection) {
            if (ancienneCollection.contains(obj)) { listeToRemove.remove(obj); }
        }
        
        
        for (T obj : ancienneCollection) {
            if (nouvelleCollection.contains(obj)) { listeToAdd.remove(obj); }
        }
        
        Set<Set<T>> superList = new HashSet<>();
        
        superList.add(listeToRemove);
        superList.add(listeToAdd);
        
        
        return superList;
    }
}
