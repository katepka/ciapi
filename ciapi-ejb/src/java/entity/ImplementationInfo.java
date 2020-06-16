package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ci_implementation_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ImplementationInfo.findAll", query = "SELECT i FROM ImplementationInfo i"),
    @NamedQuery(name = "ImplementationInfo.findById", query = "SELECT i FROM ImplementationInfo i WHERE i.id = :id")})
public class ImplementationInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "implInfo")
    private Collection<Idea> ideaCollection;
    
    @OneToMany(mappedBy = "implInfo")
    private Collection<Photo> photoCollection;

    public ImplementationInfo() {
    }

    public ImplementationInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Idea> getIdeaCollection() {
        return ideaCollection;
    }

    public void setIdeaCollection(Collection<Idea> ideaCollection) {
        this.ideaCollection = ideaCollection;
    }

    @XmlTransient
    public Collection<Photo> getPhotoCollection() {
        return photoCollection;
    }

    public void setPhotoCollection(Collection<Photo> photoCollection) {
        this.photoCollection = photoCollection;
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
        if (!(object instanceof ImplementationInfo)) {
            return false;
        }
        ImplementationInfo other = (ImplementationInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ImplementationInfo[ id=" + id + " ]";
    }
    
}
