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
@Table(name = "ci_votes_ideas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotesIdeas.findAll", query = "SELECT v FROM VotesIdeas v"),
    @NamedQuery(name = "VotesIdeas.findByUserId", query = "SELECT v FROM VotesIdeas v WHERE v.votesIdeasPK.userId = :userId"),
    @NamedQuery(name = "VotesIdeas.findByRatedIdeaId", query = "SELECT v FROM VotesIdeas v WHERE v.votesIdeasPK.ratedIdeaId = :ratedIdeaId"),
    @NamedQuery(name = "VotesIdeas.findByVote", query = "SELECT v FROM VotesIdeas v WHERE v.vote = :vote")})
public class VotesIdeas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VotesIdeasPK votesIdeasPK;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "vote")
    private short vote;
    
    @JoinColumn(name = "rated_idea_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Idea idea;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public VotesIdeas() {
    }

    public VotesIdeas(VotesIdeasPK votesIdeasPK) {
        this.votesIdeasPK = votesIdeasPK;
    }

    public VotesIdeas(VotesIdeasPK votesIdeasPK, short vote) {
        this.votesIdeasPK = votesIdeasPK;
        this.vote = vote;
    }

    public VotesIdeas(long userId, long ratedIdeaId) {
        this.votesIdeasPK = new VotesIdeasPK(userId, ratedIdeaId);
    }

    public VotesIdeasPK getVotesIdeasPK() {
        return votesIdeasPK;
    }

    public void setVotesIdeasPK(VotesIdeasPK votesIdeasPK) {
        this.votesIdeasPK = votesIdeasPK;
    }

    public short getVote() {
        return vote;
    }

    public void setVote(short vote) {
        this.vote = vote;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea idea) {
        this.idea = idea;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (votesIdeasPK != null ? votesIdeasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotesIdeas)) {
            return false;
        }
        VotesIdeas other = (VotesIdeas) object;
        if ((this.votesIdeasPK == null && other.votesIdeasPK != null) || (this.votesIdeasPK != null && !this.votesIdeasPK.equals(other.votesIdeasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VotesIdeas[ votesIdeasPK=" + votesIdeasPK + " ]";
    }
    
}
