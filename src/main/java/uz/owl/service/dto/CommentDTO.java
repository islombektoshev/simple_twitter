package uz.owl.service.dto;

import java.util.Set;

public class CommentDTO {

    private Long id;

    private Long postId;

    private UserDTO authorDTO;

    private String body;

    private Set<CommentDTO> replies;

    private CommentDTO repliedTo;

    private boolean deviceUserLiked;

    private boolean deviceUserDisliked;

    private Long likeCount;

    private Long dislikeCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public UserDTO getAuthorDTO() {
        return authorDTO;
    }

    public void setAuthorDTO(UserDTO authorDTO) {
        this.authorDTO = authorDTO;
    }

    public Set<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(Set<CommentDTO> replies) {
        this.replies = replies;
    }

    public boolean isDeviceUserLiked() {
        return deviceUserLiked;
    }

    public void setDeviceUserLiked(boolean deviceUserLiked) {
        this.deviceUserLiked = deviceUserLiked;
    }

    public boolean isDeviceUserDisliked() {
        return deviceUserDisliked;
    }

    public void setDeviceUserDisliked(boolean deviceUserDisliked) {
        this.deviceUserDisliked = deviceUserDisliked;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public CommentDTO getRepliedTo() {
        return repliedTo;
    }

    public void setRepliedTo(CommentDTO repliedTo) {
        this.repliedTo = repliedTo;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
