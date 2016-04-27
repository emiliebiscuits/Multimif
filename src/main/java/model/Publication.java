/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author banana
 */
@Entity
@Table(name = "Publication")
@NamedQueries({
    @NamedQuery(name = "Publication.getAll",
            query = "SELECT p FROM Publication p"
    ),
    @NamedQuery(
            name = "Publication.getPublicationById",
            query = "SELECT p FROM Publication p WHERE p.id LIKE :id"
    ),
    @NamedQuery(
            name = "Event.getPublicationByEventId",
            query = "SELECT p FROM Publication p WHERE p.idEvent LIKE :id"
    )
})

public class Publication implements Serializable  {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idEvent;
    private String uri;
    private String description;

    public Publication() {
    }

    
    public Publication(int id, String title, String desc) {
        this.idEvent = id;
        this.uri = title;
        this.description = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
