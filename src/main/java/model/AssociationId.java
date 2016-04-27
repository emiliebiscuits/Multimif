/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author françois
 */
//public class AssociationId{}
@Embeddable
public class AssociationId implements Serializable{
    private int idUser;
    private int idEvent;

    public AssociationId() {
    }

    public AssociationId(int user, int event){
        idUser = user;
        idEvent = event;
    }
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdConf() {
        return idEvent;
    }

    public void setIdConf(int idConf) {
        this.idEvent = idConf;
    }
}
