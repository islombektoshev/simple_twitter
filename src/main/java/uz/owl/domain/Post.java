package uz.owl.domain;

import uz.owl.service.dto.PostDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "avatar_id", updatable = false, insertable = false)
    private Long avatarId;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    @Lob
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "author_id", insertable = false, updatable = false, nullable = false)
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;


    @ManyToMany
    @JoinTable(
        name = "liked_users",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> likedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "disliked_users",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> dislikedUsers = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "regarded_users",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> regardedUsers = new HashSet<>();

    private Long likedCount;

    private Long dislikedCount;

    private Long regardedCount;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public Set<User> getDislikedUsers() {
        return dislikedUsers;
    }

    public Set<User> getRegardedUsers() {
        return regardedUsers;
    }

    public Long getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(Long likedCount) {
        this.likedCount = likedCount;
    }

    public Long getDislikedCount() {
        return dislikedCount;
    }

    public void setDislikedCount(Long dislikedCount) {
        this.dislikedCount = dislikedCount;
    }

    public Long getRegardedCount() {
        return regardedCount;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRegardedCount(Long regardedCount) {
        this.regardedCount = regardedCount;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public PostDTO toDTO() {
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(getBody());
        postDTO.setDislikedCount(getDislikedCount());
        postDTO.setId(getId());
        postDTO.setLikedCount(getLikedCount());
        postDTO.setRegardedCount(getRegardedCount());
        postDTO.setTitle(getTitle());
        postDTO.setAvatarId(getAvatarId());
        return postDTO;
    }
}
