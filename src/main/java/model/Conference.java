package model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name="Conference")
@NamedQueries({
    @NamedQuery(name="Conference.getAll",
                query="SELECT c FROM Conference c"),
    @NamedQuery(name="Conference.getAllConf",
                query="SELECT c FROM Conference c WHERE c.directeur = :user"),
    @NamedQuery(name="Conference.getAllConfChair",
                query="SELECT c FROM Conference c WHERE c.id IN"
                + "(SELECT a.id.idEvent FROM AssociationUserEvent a WHERE a.id.idUser=:idUser)"),
    @NamedQuery(name="Conference.getConferenceByName",
                query="SELECT c FROM Conference c WHERE c.nom LIKE :name"),
    @NamedQuery(name="Conference.getLastId",
                query="SELECT max(c.id) FROM Conference c"),
    @NamedQuery(name="Conference.getConferenceById",
                query="SELECT c FROM Conference c WHERE c.id LIKE :id")
})
public class Conference implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Utilisateur directeur;
    
    private String nom;
    
    private String theme;
    
    private String description;
    
    private Date dateCreation;

    
    /*************/
    /*Constructor*/
    /*************/
    
    
    
    /**
    * Constructeur par défaut de Conference 
    */
    public Conference() {
    }
    
    /**
     * Constructeur de la classe Conference
     * @param director : Directeur de la conference
     * @param nom
     * @param theme
     */
    public Conference(Utilisateur director, String nom, String theme){
        this.directeur = director;
        this.nom = nom;
        this.theme = theme;
    }
    
    /**
     * Constructeur par copie de la classe Conference
     * @param c : la conference à copier
     */
    public Conference(Conference c){
        id = c.id;
        nom            = c.nom;
        directeur = c.directeur;
        theme          = c.theme;
        dateCreation = c.dateCreation;
    }

    /*********/
    /*Setters*/
    /*********/
    
//    public void setIdConf(ConferenceId cid){
//        id=cid;
//    }
    
    /**
     * Setter de l'attribut Name
     * @param name : le nom de la conference
     */
    public void setNom(String name) {
        this.nom = name;
    }

    /**
     * Setter de l'attribut theme
     * @param theme : le theme de la conference
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*********/
    /*Getters*/
    /*********/
    
//    @EmbeddedId
//    public ConferenceId getIdConf(){
//        return id;
//    }
    
    /**
     * Getter de l'attribut id
     * @return l'attribut id de l'objet
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de l'attribut Name
     * @return l'attribut name de l'objet
     */
    public String getName() {
        return nom;
    }

    /**
     * Getter de l'attribut director_id
     * @return l'attribut director_id de l'objet
     */
    public Utilisateur getDirector() {
        return directeur;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter de l'attribut theme
     * @return l'attribut theme de l'objet
     */
    public String getTheme() {
        return theme;
    }
    
    /**********/
    /*Methodes*/
    /**********/
    
    @Override
    public String toString(){
        String res="";
        res += (nom == null ? "" : "Nom : " + nom);
        res += (theme == null ? "" : " Theme : " + theme + "\n");
        res += (description == null ? "" : "Description : " + description + "\n");
        res += (dateCreation == null ? "" : "Date : " + dateCreation.toString());
        return res;        
    }

    public Date getDate() {
        return dateCreation;
    }

    public void setDate(Date date) {
        this.dateCreation = date;
    }
    
    public String getWebSite(){
        return "aFaire Site";
    }
    
    public String getPlace(){
        return "aFaire Place";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Conference other = (Conference) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.directeur, other.directeur)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.theme, other.theme)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.dateCreation, other.dateCreation)) {
            return false;
        }
        return true;
    }
    
    
}
