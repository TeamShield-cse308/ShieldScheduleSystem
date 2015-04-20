/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The class representing Administrator users in our database
 * @author Phillip Elliot
 */
@NamedQueries({
    @NamedQuery(name = "Administrator.findByUsername",
            query = "SELECT a FROM Administrator a WHERE a.username = :username"),
})
@Entity
public class Administrator extends GenericUser implements Serializable
{

    private static final long serialVersionUID = 1L;
    
    //Username is not changeable
    //Administrators are created and edited via manual database query
    @Id
    private String username;

    //requirement for JPA
    protected Administrator(){}
    
    
    public String getUsername()
    {
        return username;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the username fields are not set
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "cse308.Admin[ id=" + username + " ]";
    }

}
