/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import javax.persistence.MappedSuperclass;

/**
 * Class representing generic users in the database
 * @author Phillip Elliot
 */
@MappedSuperclass
public abstract class GenericUser
{
    protected String name;
    protected String password;
    
    public String getName()
    {
        return name;
    }
    
    public String getPassword()
    {
        return password;
    }
    
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
