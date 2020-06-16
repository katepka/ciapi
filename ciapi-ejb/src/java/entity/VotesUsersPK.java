package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class VotesUsersPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_id")
    private long userId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "rated_user_id")
    private long ratedUserId;

    public VotesUsersPK() {
    }

    public VotesUsersPK(long userId, long ratedUserId) {
        this.userId = userId;
        this.ratedUserId = ratedUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRatedUserId() {
        return ratedUserId;
    }

    public void setRatedUserId(long ratedUserId) {
        this.ratedUserId = ratedUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) ratedUserId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VotesUsersPK)) {
            return false;
        }
        VotesUsersPK other = (VotesUsersPK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.ratedUserId != other.ratedUserId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VotesUsersPK[ userId=" + userId + ", ratedUserId=" + ratedUserId + " ]";
    }
    
}
