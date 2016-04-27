/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

import java.util.ArrayList;

/**
 *
 * @author sheep
 */
public class Organization {
    private String id;
    private String label;
    private ArrayList<String> members;
    
    /**
     * Constructeur de base de la classe Organization
     * @param id L'id de l'organisation
     */
    public Organization(String id){
        this.id = "http://data.semanticweb.org/organization/" + id;
        members = new ArrayList<>();
    }
    
    /**
     * Constructeur par copier de Organization
     * @param o L'organisation à copier
     */
    public Organization(Organization o){
        this.id      = o.id;
        this.label   = o.label;
        this.members = new ArrayList<>(o.members);
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", label=" + label + ", members=" + members + '}';
    }
    
    
}
