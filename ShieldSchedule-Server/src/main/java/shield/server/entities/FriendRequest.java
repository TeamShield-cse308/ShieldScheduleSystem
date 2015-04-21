/*

 */
package shield.server.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Jeffrey Kabot
 */
@NamedQueries({
    @NamedQuery(name = "FriendRequest.findByRecpient",
            query = "SELECT fr FROM FriendRequest fr WHERE fr.recipient.email = :recipient"),  
    @NamedQuery(name = "FriendRequset.findBySenderAndRecipient",
            query = "SELECT fr FROM FriendRequest fr WHERE fr.sender.email = :sender AND fr.recipient.email = :recipient"),
})
@Entity
public class FriendRequest implements Serializable
{
    //@TODO handle unique sender and recipient combination
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Student sender;
    private Student recipient;

    protected FriendRequest()
    {
    }

    public FriendRequest(Student sndr,
            Student rcpnt)
    {
        sender = sndr;
        recipient = rcpnt;
    }

    public Student getSender()
    {
        return sender;
    }

    public Student getRecipient()
    {
        return recipient;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FriendRequest))
        {
            return false;
        }
        FriendRequest other = (FriendRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "shield.server.entities.FriendRequest[ id=" + id + " ]";
    }

}
