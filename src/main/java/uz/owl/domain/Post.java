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

    @Lob
    @Column(name = "image_url")
    private String imageUrl;


    @ManyToMany
    @JoinTable(
        name = "liked_users",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> likedUsers = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "disliked_users",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> dislikedUsers = new HashSet<>();

    @ManyToMany
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

    public void setRegardedCount(Long regardedCount) {
        this.regardedCount = regardedCount;
    }
    public PostDTO toDTO(){
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(getBody());
        postDTO.setDislikedCount(getDislikedCount());
        postDTO.setId(getId());
        postDTO.setImageUrl(getImageUrl());
        postDTO.setLikedCount(getLikedCount());
        postDTO.setRegardedCount(getRegardedCount());
        postDTO.setTitle(getTitle());
        return postDTO;
    }
}
