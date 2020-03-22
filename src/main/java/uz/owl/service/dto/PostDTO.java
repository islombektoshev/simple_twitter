package uz.owl.service.dto;


import uz.owl.service.RatingType;

public class PostDTO {

    private Long id;

    private String title;

    private String body;


    private Long likedCount;

    private Long dislikedCount;

    private Long regardedCount;

    private UserDTO author;

    private Long avatarId;


    private RatingType ratingType;

    private boolean regarded;

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

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public boolean isRegarded() {
        return regarded;
    }

    public void setRegarded(boolean regarded) {
        this.regarded = regarded;
    }
}
