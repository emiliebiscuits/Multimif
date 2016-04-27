/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

/**
 *
 * @author Clément
 */
public class Location {
    private String id;
    private String name;
    
    /**
     * Constructeur de base de la classe Location
     * @param id L'id de la Location
     */
    public Location(String id) {
        this.id    = "http://data.semanticweb.org/conference/eswc/2015/location/" + id;
    }
    
    /**
     * Constructeur par copie de la classe Location
     * @param l Location à copier
     */
    public Location (Location l){
        this.id    = l.id;
        this.name = l.name;
    }

    public String getId() {
        return id;
    }   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + '}';
    }
        
    
}
