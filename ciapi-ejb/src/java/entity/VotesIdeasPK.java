package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class VotesIdeasPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private long userId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "rated_idea_id")
    private long ratedIdeaId;

    public VotesIdeasPK() {
    }

    public VotesIdeasPK(long userId, long ratedIdeaId) {
        this.userId = userId;
        this.ratedIdeaId = ratedIdeaId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRatedIdeaId() {
        return ratedIdeaId;
    }

    public void setRatedIdeaId(long ratedIdeaId) {
        this.ratedIdeaId = ratedIdeaId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) ratedIdeaId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotesIdeasPK)) {
            return false;
        }
        VotesIdeasPK other = (VotesIdeasPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.ratedIdeaId != other.ratedIdeaId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VotesIdeasPK[ userId=" + userId + ", ratedIdeaId=" + ratedIdeaId + " ]";
    }
    
}
