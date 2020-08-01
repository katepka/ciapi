package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "ci_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByName", query = "SELECT u FROM User u WHERE u.name = :name"),
    @NamedQuery(name = "User.findUser", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password"),
    @NamedQuery(name = "User.findByRole", query = "SELECT u FROM User u JOIN u.role s WHERE s.id = :roleId")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Collection<Idea> ideaCollection;
    
    @OneToMany(mappedBy = "coordinator")
    private Collection<Idea> ideaCollection1;
    
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Role role;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratedUser")
    private Collection<VotesUsers> votesUsersCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<VotesUsers> votesUsersCollection1;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<VoteIdeas> votesIdeasCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Collection<Comment> commentCollection;
    
    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String email, String password, String name) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Idea> getIdeaCollection() {
        return ideaCollection;
    }

    public void setIdeaCollection(Collection<Idea> ideaCollection) {
        this.ideaCollection = ideaCollection;
    }

    @XmlTransient
    public Collection<Idea> getIdeaCollection1() {
        return ideaCollection1;
    }

    public void setIdeaCollection1(Collection<Idea> ideaCollection1) {
        this.ideaCollection1 = ideaCollection1;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roleId) {
        this.role = roleId;
    }

    @XmlTransient
    public Collection<VotesUsers> getVotesUsersCollection() {
        return votesUsersCollection;
    }

    public void setVotesUsersCollection(Collection<VotesUsers> votesUsersCollection) {
        this.votesUsersCollection = votesUsersCollection;
    }

    @XmlTransient
    public Collection<VotesUsers> getVotesUsersCollection1() {
        return votesUsersCollection1;
    }

    public void setVotesUsersCollection1(Collection<VotesUsers> votesUsersCollection1) {
        this.votesUsersCollection1 = votesUsersCollection1;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.User[ id=" + id + " ]";
    }
    
}
