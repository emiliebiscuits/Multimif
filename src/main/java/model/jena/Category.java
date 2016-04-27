/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jena;

/**
 *
 * @author sheep
 */
public class Category {
    private String id;
    private String label;
    private String name;
    
    /**
     * Constructeur de la classe Categorie.
     * @param id Id de la Catégorie
     */
    public Category(String id){
        this.id = "http://data.semanticweb.org/conference/category/" + id;
    }
    
    /**
     * Constructeur par copie de Categorie
     * @param c Une Categorie par copie
     */
    public Category(Category c){
        id    = c.id;
        label = c.label;
        name  = c.name;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", label=" + label + ", name=" + name + '}';
    }
}
