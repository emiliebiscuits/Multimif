/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

import java.util.ArrayList;
import model.Utilisateur;

/**
 *
 * @author sheep
 */
public class Person {
    private String accounts;
    private String affiliation;
    private String depiction;
    private ArrayList<String> holdsRole;
    private String id;
    private ArrayList<String> made;
    private String mbox;
    private String name;
    private ArrayList<String> websites;
    
    /**
     * Constructeur de base de Person
     * @param id Id de la personne
     */
    public Person(String id){
        this.id   = "http://data.semanticweb.org/person/" + id;
        holdsRole = new ArrayList<>();
        made      = new ArrayList<>();
        websites  = new ArrayList<>();
    }
    
    /**
     * Constructeur par copie de Person
     * @param p La Personne a copier
     */
    public Person(Person p){
        this.accounts    = p.accounts;
        this.affiliation = p.affiliation;
        this.depiction   = p.depiction;
        this.holdsRole   = new ArrayList<>(p.holdsRole);
        this.id          = p.id;
        this.made        = new ArrayList<>(p.made);
        this.mbox        = p.mbox;
        this.name        = p.name;
        this.websites    = new ArrayList<>(p.websites);
    }

    public String getAccounts() {
        return accounts;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getDepiction() {
        return depiction;
    }

    public ArrayList<String> getHoldsRole() {
        return holdsRole;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getMade() {
        return made;
    }

    public String getMbox() {
        return mbox;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getWebsites() {
        return websites;
    }

    public void setAccounts(String accounts) {
        this.accounts = accounts;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public void setHoldsRole(ArrayList<String> holdsRole) {
        this.holdsRole = holdsRole;
    }

    public void setMade(ArrayList<String> made) {
        this.made = made;
    }

    public void setMbox(String mbox) {
        this.mbox = mbox;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsites(ArrayList<String> websites) {
        this.websites = websites;
    }

    @Override
    public String toString() {
        return "Person{" + "accounts=" + accounts + ", affiliation=" + affiliation + ", depiction=" + depiction + ", holdsRole=" + holdsRole + ", id=" + id + ", made=" + made + ", mbox=" + mbox + ", name=" + name + ", websites=" + websites + '}';
    }
    
    public static Person toPerson(Utilisateur u){
        Person res = new Person("http://data.semanticweb.org/person/"+u.getPrenom().toLowerCase()+"-"+u.getNom().toLowerCase());
        res.setName(u.getPrenom()+" "+u.getNom());
        
        //Affiliation : a voir pour récuperer si elle existe
        res.setAffiliation(
                "http://data.semanticweb.org/organization/"+u.getAffiliation()
                                                            .replace(" ", "-")
                                                            .toLowerCase()
        );
        
        ArrayList<String> tmp = new ArrayList<>();
        tmp.add(u.getpageWeb());
        res.setWebsites(tmp);
        
        return res;
    }
}
