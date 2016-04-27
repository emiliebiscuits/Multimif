/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author françois
 */
@Entity
@Table(name = "Event")
@NamedQueries({
    @NamedQuery(name = "Event.getAll",
            query = "SELECT e FROM Event e"
    ),
    @NamedQuery(name = "Event.getAllByParent",
            query = "SELECT e FROM Event e WHERE e.idConf LIKE :id"
    ),
    @NamedQuery(
            name = "Event.getEventById",
            query = "SELECT e FROM Event e WHERE e.id LIKE :id"
    ),
    @NamedQuery(
            name = "Event.getEventByConfId",
            query = "SELECT e FROM Event e WHERE e.idConf LIKE :id"
    )
})

public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private int idConf;
    private String uri;

    public Event() {

    }

    public Event(int idConf, String type, String uri) {
        this.idConf = idConf;
        this.type = type;
        this.uri = uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIdConf(int idConf) {
        this.idConf = idConf;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getIdConf() {
        return idConf;
    }

    public Event(String type, int idConf) {
        this.type = type;
    }

    public boolean typeValide(String type) {
        return (type.equals("conference") || type.equals("publication")
                || type.equals("keynote") || type.equals("panel")
                || type.equals("evenement") || type.equals("autre"));
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.idConf, other.idConf)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.uri, other.uri)) {
            return false;
        }
        return true;
    }
}
