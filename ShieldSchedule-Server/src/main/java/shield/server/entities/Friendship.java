package shield.server.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

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
                    query = "SELECT f FROM Friendship f WHERE f.approved = false AND f.visible = true AND f.recipient.email = :email"
            ),
            @NamedQuery(
                    name = "Friendship.findBySenderAndRecipient",
                    query = "SELECT f FROM Friendship f WHERE f.sender.email = :sender AND f.recipient.email = :recipient"
            ),})
@Table(uniqueConstraints
        = @UniqueConstraint(columnNames
                = {
            "SENDER_ID, RECIPIENT_ID"
        }))
public class Friendship implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //Student who sent the Friendship request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    private Student sender;

    //Student receiving the Friendship request
    @Index
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECIPIENT_ID")
    private Student recipient;

    //If the Friendship is approved.  New Friendships start out not approved.
    @Index
    private boolean approved;

    //Reference to a Friendship whose sender->recipient direction is inverted
    //Friendships are created in pairs to maintain symmetry.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Friendship inverse;

    //Slave Friendships (created secondary to the initial Friendship object) are
    //invisible to certain queries.
    private boolean visible;

    protected Friendship() {
    }

    /**
     * Creates a new Friendship associating two students. Friendships start off
     * not approved.
     *
     * @param sndr The Student sending the Friendship request.
     * @param rcpnt The Student receiving the Friendship Request.
     */
    public Friendship(Student sndr, Student rcpnt) {
        sender = sndr;
        recipient = rcpnt;
        approved = false;
        visible = true;
        inverse = new Friendship(this); //create the slave Friendship
    }

    //Creating a Friendship automatically creates a new Slave Friendship in the
    //opposite direction.  This ensures symmetry within the friendship graph.
    //Consequently, for queries that are agnostic to who sent the Friendship request,
    //both students are the sender and the recipient.
    //For queries that care, slave friendships are invisible and won't interfere.
    //The inverse friendship is approved when its partner friendship is approved.
    private Friendship(Friendship f) {
        sender = f.recipient;
        recipient = f.sender;
        approved = false;
        visible = false;
        inverse = f;
    }

    public Student getSender() {
        return sender;
    }

    public Student getRecipient() {
        return recipient;
    }

    /**
     * Approve a friend request.
     */
    public void approve() {
        approved = true;
        inverse.approved = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Friendship)) {
            return false;
        }
        Friendship other = (Friendship) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(
                other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "shield.server.entities.Friendship[ id=" + id + " ]";
    }

}
