package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@NamedQueries({
    @NamedQuery(
        name="Utilisateur.getAllUser",
        query="SELECT u FROM Utilisateur u"
    ),
    @NamedQuery(
        name="Utilisateur.getAllUserLike",
        query="SELECT u FROM Utilisateur u WHERE u.mail LIKE :name"
    ),
    @NamedQuery(
        name="Utilisateur.getUserByMail",
        query="SELECT u FROM Utilisateur u WHERE u.mail LIKE :mail"
    ),
    @NamedQuery(
        name="Utilisateur.getUserByPseudo",
        query="SELECT u FROM Utilisateur u WHERE u.pseudo LIKE :pseudo"
    ),
    @NamedQuery(
        name="Utilisateur.getUserById",
        query="SELECT u FROM Utilisateur u WHERE u.id LIKE :id"
    ),
    @NamedQuery(
        name="Utilisateur.getLastId",
        query="SELECT max(u.id) FROM Utilisateur u"
    ),
    @NamedQuery(
        name="Utilisateur.getAllUserByConf",
        query="SELECT u FROM Utilisateur u WHERE u.id IN "
                + "(SELECT a.id.idUser FROM AssociationUserEvent a WHERE a.id.idEvent=:idConf)"
    )
        
})
@Table(name="Utilisateur")  
public class Utilisateur  implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    private String mail;
    private String prenom;
    private String nom;
    private String pays;    
    private String pageWeb;    
    private String affiliation;
    private String pseudo;
    private String motDePasse; 
    private long idFacebook;
    private long idTwitter;
    private long idGravatar;
    
    /*************/
    /*Constructor*/
    /*************/
    
    /**
     * Constructeur vide de la classe Utilisateur
     */
    protected Utilisateur(){
    }
    
    /**
     * Constructeur de la classe Utilisateur
     * @param mail : addresse mail de l'utilisteur
     */
    public Utilisateur(String mail){
        this.mail = mail;
    }
    
    /**
     * Constructeur par copie de la classe Utilisateur
     * @param u : l'utilisateur à copier
     */
    public Utilisateur(Utilisateur u){
        id          = u.id;
        mail        = u.mail;
        prenom  = u.prenom;
        nom   = u.nom;
        pays     = u.pays;
        pageWeb    = u.pageWeb;
        affiliation = u.affiliation;
        pseudo = u.pseudo;
        motDePasse = u.motDePasse;
        idFacebook = u.idFacebook;
        idGravatar = u.idGravatar;
        idTwitter = u.idTwitter;
    }
    
    /*********/
    /*Setters*/
    /*********/
    
    /**
     * Setter de l'attribut mail
     * @param email
     */
    public void setMail(String email) {
        this.mail = email;
    }
    
    /**
     * Setter de l'attribut first_name
     * @param first_name
     */
    public void setFirst_name(String first_name) {
        this.prenom = first_name;
    }

    /**
     * Setter de l'attribut last_name
     * @param last_name
     */
    public void setLast_name(String last_name) {
        this.nom = last_name;
    }

    /**
     * Setter de l'attribut country
     * @param country
     */
    public void setCountry(String country) {
        this.pays = country;
    }

    /**
     * Setter de l'attribut web_page
     * @param web_page
     */
    public void setWeb_page(String web_page) {
        this.pageWeb = web_page;
    }

    /**
     * Setter de l'attribut affiliation
     * @param affiliation
     */
    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    /*********/
    /*Getters*/
    /*********/
    
    /**
     * Getter de l'attribut id
     * @return Id de l'objet Utilisateur
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de l'attribut mail
     * @return Mail de l'objet Utilisateur
     */
    public String getMail() {
        return mail;
    }

    /**
     * Getter de l'attribut first_name
     * @return Prénom de l'objet Utilisateur
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Getter de l'attribut last_name
     * @return Nom de famille de l'objet Utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de l'attribut country
     * @return Pays de l'objet Utilisateur
     */
    public String getPays() {
        return pays;
    }

    /**
     * Getter de l'attribut web_page
     * @return Page web de l'objet Utilisateur
     */
    public String getpageWeb() {
        return pageWeb;
    }

    /**
     * Getter de l'attribut affiliation
     * @return affiliation de l'objet Utilisateur
     */
    public String getAffiliation() {
        return affiliation;
    }

    /**********/
    /*Methodes*/
    /**********/
    
    /**
     * Méthode toString de la classe Utilisateur.
     * @return Utilisateur au format String
     */
    @Override
    public String toString(){
        String res = "";
        
        res += "mail : "+this.mail+"\n";
        if(this.nom != null)
            res+= "nom : "+this.nom+"\n";
        if(this.pseudo != null)
            res+= "Pseudo : "+this.pseudo+"\n";
        if(this.prenom != null)
            res+= "prenom : "+this.prenom+"\n";
        if(this.pays != null)
            res+= "pays : "+this.pays+"\n";
        if(this.pageWeb != null)
            res+= "page Web : "+this.pageWeb+"\n";
        if(this.affiliation != null)
            res+= "affiliation : "+this.affiliation+"\n";
        return res;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public long getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(long idFacebook) {
        this.idFacebook = idFacebook;
    }

    public long getIdTwitter() {
        return idTwitter;
    }

    public void setIdTwitter(long idTwitter) {
        this.idTwitter = idTwitter;
    }

    public long getIdGravatar() {
        return idGravatar;
    }

    public void setIdGravatar(long idGravatar) {
        this.idGravatar = idGravatar;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        hash = 89 * hash + Objects.hashCode(this.mail);
        hash = 89 * hash + Objects.hashCode(this.prenom);
        hash = 89 * hash + Objects.hashCode(this.nom);
        hash = 89 * hash + Objects.hashCode(this.pseudo);
        hash = 89 * hash + Objects.hashCode(this.motDePasse);
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
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.pseudo, other.pseudo)) {
            return false;
        }
        if (!Objects.equals(this.motDePasse, other.motDePasse)) {
            return false;
        }
        return true;
    }
    
    
}
