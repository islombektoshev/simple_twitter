package uz.owl.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "body")
    private String body;

    @Column(name = "post_id", insertable = false, updatable = false, nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "author_id", nullable = false, updatable = false, insertable = false)
    private Long authorId;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;


    @Column(name = "replied_id", insertable = false, updatable = false)
    private Long repliedCommentId;

    @ManyToOne
    @JoinColumn(name = "replied_id")
    private Comment repliedComment;

    @ManyToMany
    @JoinTable(
        name = "comment_liked",
        joinColumns = @JoinColumn(name = "comment_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> likedUser = new HashSet<>();


    @ManyToMany
    @JoinTable(
        name = "comment_disliked",
        joinColumns = @JoinColumn(name = "comment_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private final Set<User> dislikedUser = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Long getRepliedCommentId() {
        return repliedCommentId;
    }

    public void setRepliedCommentId(Long repliedCommentId) {
        this.repliedCommentId = repliedCommentId;
    }

    public Set<User> getLikedUser() {
        return likedUser;
    }

    public Set<User> getDislikedUser() {
        return dislikedUser;
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

    public Comment getRepliedComment() {
        return repliedComment;
    }

    public void setRepliedComment(Comment repliedComment) {
        this.repliedComment = repliedComment;
    }
}
