package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ci_idea")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Idea.findAll", query = "SELECT i FROM Idea i"),
    @NamedQuery(name = "Idea.findById", query = "SELECT i FROM Idea i WHERE i.id = :id"),
    @NamedQuery(name = "Idea.findByTitle", query = "SELECT i FROM Idea i WHERE i.title = :title"),
    @NamedQuery(name = "Idea.findByCreated", query = "SELECT i FROM Idea i WHERE i.created = :created"),
    @NamedQuery(name = "Idea.findByDescription", query = "SELECT i FROM Idea i WHERE i.description = :description"),
    @NamedQuery(name = "Idea.findByCategory", query = "SELECT i FROM Idea i JOIN i.category s WHERE s.id = :categoryId"),
    @NamedQuery(name = "Idea.findByLocation", query = "SELECT i FROM Idea i JOIN i.location s WHERE s.id = :locationId"),
    @NamedQuery(name = "Idea.findByAuthor", query = "SELECT i FROM Idea i JOIN i.author s WHERE s.id = :authorId"),
    @NamedQuery(name = "Idea.findByCoordinator", query = "SELECT i FROM Idea i JOIN i.coordinator s WHERE s.id = :coordinatorId"),
    @NamedQuery(name = "Idea.findByStatus", query = "SELECT i FROM Idea i JOIN i.status s WHERE s.id = :statusId"),
    @NamedQuery(name = "Idea.countByStatus", query = "SELECT COUNT(i) FROM Idea i JOIN i.status s WHERE s.id = :statusId"),
    @NamedQuery(name = "Idea.countByCategory", query = "SELECT COUNT(i) FROM Idea i JOIN i.category c WHERE c.id = :categoryId")})
public class Idea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "title")
    private String title;
    
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "description")
    private String description;
    
    @Basic(optional = false)
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User author;
    
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Category category;
    
    @JoinColumn(name = "coordinator_id", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private User coordinator;
    
    @JoinColumn(name = "impl_info_id", referencedColumnName = "id")
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    private ImplementationInfo implInfo;
    
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    private Location location;
    
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;
    
    @Basic(optional = true)
    @Column(name = "photoRef")
    private String photoRef;
    
    @OneToMany(mappedBy = "idea")
    private Collection<Photo> photoCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idea")
    private Collection<VoteIdeas> votesIdeasCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idea")
    private Collection<Comment> commentCollection;

    public Idea() {
    }

    public Idea(Long id) {
        this.id = id;
    }

    public Idea(Long id, String title, String description, Date created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    public ImplementationInfo getImplInfo() {
        return implInfo;
    }

    public void setImplInfo(ImplementationInfo implInfo) {
        this.implInfo = implInfo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }

    @XmlTransient
    public Collection<Photo> getPhotoCollection() {
        return photoCollection;
    }

    public void setPhotoCollection(Collection<Photo> photoCollection) {
        this.photoCollection = photoCollection;
    }

    @XmlTransient
    public Collection<VoteIdeas> getVotesIdeasCollection() {
        return votesIdeasCollection;
    }

    public void setVotesIdeasCollection(Collection<VoteIdeas> votesIdeasCollection) {
        this.votesIdeasCollection = votesIdeasCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Idea)) {
            return false;
        }
        Idea other = (Idea) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Idea{" + "id=" + id 
                + ", title=" + title 
                + ", description=" + description 
                + ", created=" + created 
                + ", author=" + author 
                + ", category=" + category 
                + ", coordinator=" + coordinator 
                + ", implInfo=" + implInfo 
                + ", location=" + location 
                + ", status=" + status 
                + ", photoRef=" + photoRef + '}';
    }

}
