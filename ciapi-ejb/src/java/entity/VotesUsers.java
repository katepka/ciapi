package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ci_votes_users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotesUsers.findAll", query = "SELECT v FROM VotesUsers v"),
    @NamedQuery(name = "VotesUsers.findByUserId", query = "SELECT v FROM VotesUsers v WHERE v.votesUsersPK.userId = :userId"),
    @NamedQuery(name = "VotesUsers.findByRatedUserId", query = "SELECT v FROM VotesUsers v WHERE v.votesUsersPK.ratedUserId = :ratedUserId"),
    @NamedQuery(name = "VotesUsers.findByVote", query = "SELECT v FROM VotesUsers v WHERE v.vote = :vote")})
public class VotesUsers implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotesUsersPK votesUsersPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "vote")
    private short vote;
    
    @JoinColumn(name = "rated_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User ratedUser;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public VotesUsers() {
    }

    public VotesUsers(VotesUsersPK votesUsersPK) {
        this.votesUsersPK = votesUsersPK;
    }

    public VotesUsers(VotesUsersPK votesUsersPK, short vote) {
        this.votesUsersPK = votesUsersPK;
        this.vote = vote;
    }

    public VotesUsers(long userId, long ratedUserId) {
        this.votesUsersPK = new VotesUsersPK(userId, ratedUserId);
    }

    public VotesUsersPK getVotesUsersPK() {
        return votesUsersPK;
    }

    public void setVotesUsersPK(VotesUsersPK votesUsersPK) {
        this.votesUsersPK = votesUsersPK;
    }

    public short getVote() {
        return vote;
    }

    public void setVote(short vote) {
        this.vote = vote;
    }

    public User getUser() {
        return ratedUser;
    }

    public void setUser(User user) {
        this.ratedUser = user;
    }

    public User getUser1() {
        return user;
    }

    public void setUser1(User user1) {
        this.user = user1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votesUsersPK != null ? votesUsersPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotesUsers)) {
            return false;
        }
        VotesUsers other = (VotesUsers) object;
        if ((this.votesUsersPK == null && other.votesUsersPK != null) || (this.votesUsersPK != null && !this.votesUsersPK.equals(other.votesUsersPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VotesUsers[ votesUsersPK=" + votesUsersPK + " ]";
    }
    
}
