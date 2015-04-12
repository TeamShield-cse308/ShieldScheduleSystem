/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Class representing generic users in the database
 * @author Phillip Elliot
 */
public abstract class GenericUser implements Serializable
{

//    private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    protected String name;
    protected String password;
    
    //required by JPA
    //protected GenericUser() {}
    
    /*public GenericUser(String initName, String initPassword)
    {
        //@TODO validity checking on name and password
        name = initName;
        password = initPassword;
    }*/

//    public Long getId()
//    {
//        return id;
//    }
//
//    public void setId(Long id)
//    {
//        this.id = id;
//    }
//
//    @Override
//    public int hashCode()
//    {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object)
//    {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof GenericUser)) {
//            return false;
//        }
//        GenericUser other = (GenericUser) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString()
//    {
//        return "cse308.Guest[ id=" + id + " ]";
//    }

}
