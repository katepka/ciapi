package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ci_vote_ideas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VotesIdeas.findAll", query = "SELECT v FROM VotesIdeas v"),
    @NamedQuery(name = "VotesIdeas.findByVote", query = "SELECT v FROM VotesIdeas v WHERE v.vote = :vote"),
    @NamedQuery(name = "VotesIdeas.findByUserIdAndRatedIdeaId", query = "SELECT v FROM VotesIdeas v "
            + "JOIN v.user u JOIN v.idea i WHERE u.id = :userId"
            + " and i.id = :ratedIdeaId"),
    @NamedQuery(name = "VotesIdeas.findById", query = "SELECT v FROM VotesIdeas v WHERE v.id = :id")})
public class VoteIdeas implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "vote")
    private short vote;
    
    @JoinColumn(name = "rated_idea_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Idea idea;
    
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

    public VoteIdeas() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VoteIdeas other = (VoteIdeas) obj;
        if (this.vote != other.vote) {
            return false;
        }
        if (!Objects.equals(this.idea, other.idea)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VoteIdeas{" + "id=" + id 
                + ", vote=" + vote 
                + ", idea=" + idea 
                + ", user=" + user + '}';
    }

    
    
}
