/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.eclipse.persistence.annotations.Index;

/**
 * Class representing the friendship connection between two users. Friendship is
 * a graph G = (V,E) with students as vertices and friendships as edges. In the
 * database the graph is most efficiently represented as an edge list.
 *
 * @author Jeffrey Kabot
 */
@Entity
@NamedQueries(
{
    @NamedQuery(
            name = "Friendship.getFriends",
            query = "SELECT f.sender FROM Friendship f WHERE f.approved = true AND f.recipient.email = :email"
    ),
    @NamedQuery(
            name = "Friendship.getFriendRequests",
            query = "SELECT f FROM Friendship f WHERE f.approved = false AND f.recipient.email = :email"
    ),
    @NamedQuery(
            name = "Friendship.findBySenderAndRecipient",
            query = "SELECT f FROM Friendship f WHERE f.approved = false AND f.sender.email = :sender AND f.recipient.email = :recipient"
    ),
})
@Table(uniqueConstraints
        = @UniqueConstraint(columnNames =
                {
                    "SENDER_ID, RECIPIENT_ID"
        }))
public class Friendship implements Serializable
{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //friendX is always the sender
    //the students involved are referenced within the friendship object
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private Student sender;

    //friendY is always the recipient
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPIENT_ID")
    private Student recipient;

    //friend requests are not yet approved
    @Index
    private boolean approved;

    protected Friendship()
    {
    }

    public Friendship(Student sndr, Student rcpnt)
    {
        sender = sndr;
        recipient = rcpnt;
        approved = false;
    }

    public Student getSender()
    {
        return sender;
    }

    public Student getRecipient()
    {
        return recipient;
    }

    //@TODO throw exception if already approved?
    /**
     * Approve a friend request you've received.
     */
    public void approve()
    {
        approved = true;
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
        if (!(object instanceof Friendship))
        {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(
                other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "shield.server.entities.Friendship[ id=" + id + " ]";
    }

}
