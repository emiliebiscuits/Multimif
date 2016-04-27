/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author françois
 */

@Entity 

@NamedQueries({
    @NamedQuery(name="Assoc.getAll",
                query="SELECT A FROM AssociationUserEvent a")
})
@Table(name="AssociationUserEvent")
public class AssociationUserEvent implements Serializable{
    @EmbeddedId
    private AssociationId id;

    public AssociationUserEvent() {
    }
    
    public AssociationUserEvent(Utilisateur user, Event event){
        id = new AssociationId(user.getId(),event.getId());
    }
    
    public AssociationUserEvent(int user, int event){
        id = new AssociationId(user,event);
    }

    public AssociationId getId() {
        return id;
    }

    public void setId(AssociationId id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AssociationUserEvent other = (AssociationUserEvent) obj;
        if (this.id.getIdConf() != other.id.getIdConf()) {
            return false;
        }
        if (!Objects.equals(this.id.getIdUser(), other.id.getIdUser())) {
            return false;
        }
        return true;
    }
}
