package uz.owl.service.dto;


public class PostDTO {

    private Long id;

    private String title;

    private String body;

    private String imageUrl;

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
}
