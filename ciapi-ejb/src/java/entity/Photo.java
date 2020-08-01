package entity;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "ci_photo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
    @NamedQuery(name = "Photo.findById", query = "SELECT p FROM Photo p WHERE p.id = :id"),
    @NamedQuery(name = "Photo.findByPhotoRef", query = "SELECT p FROM Photo p WHERE p.photoRef = :photoRef"),
    @NamedQuery(name = "Photo.findByIdea", query = "SELECT p FROM Photo p JOIN p.idea s WHERE s.id = :ideaId"),
    @NamedQuery(name = "Photo.findByImplInfo", query = "SELECT p FROM Photo p JOIN p.implInfo s WHERE s.id = :implInfoId")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "photo_ref")
    private String photoRef;
    
    @JoinColumn(name = "idea_id", referencedColumnName = "id")
    @ManyToOne
    private Idea idea;
    
    @JoinColumn(name = "impl_info_id", referencedColumnName = "id")
    @ManyToOne
    private ImplementationInfo implInfo;

    public Photo() {
    }

    public Photo(Long id) {
        this.id = id;
    }

    public Photo(Long id, String photoRef) {
        this.id = id;
        this.photoRef = photoRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    public Idea getIdea() {
        return idea;
    }

    public void setIdea(Idea ideaId) {
        this.idea = ideaId;
    }

    public ImplementationInfo getImplInfo() {
        return implInfo;
    }

    public void setImplInfo(ImplementationInfo implInfo) {
        this.implInfo = implInfo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Photo[ id=" + id + " ]";
    }
    
}
