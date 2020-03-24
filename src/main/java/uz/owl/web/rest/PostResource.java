package uz.owl.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.owl.service.CommentService;
import uz.owl.service.PostService;
import uz.owl.service.PostSortType;
import uz.owl.service.RatingType;
import uz.owl.service.dto.CommentDTO;
import uz.owl.service.dto.PostDTO;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class PostResource {

    private final PostService postService;
    private final CommentService commentService;


    public PostResource(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/post/type")
    public ResponseEntity<List<PostDTO>> getPostsBySortType(PostSortType postSortType) {
        return ResponseEntity.ok(postService.getPostBySortType(postSortType));
    }

    @PostMapping("/post")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {

        return ResponseEntity.ok(postService.crateNewPost(postDTO));
    }

    @PostMapping("/post/{id}/rating")
    public ResponseEntity<PostDTO> rating(RatingType ratingType, @PathVariable Long id) {
        return ResponseEntity.ok(postService.saveRating(ratingType, id));
    }

    @PostMapping("/post/{id}/remove-rating")
    public ResponseEntity<PostDTO> removeRating(RatingType ratingType, @PathVariable Long id) {
        return ResponseEntity.ok(postService.removeRating(ratingType, id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable Long id) {
        postDTO.setId(id);
        PostDTO updatePost = postService.updatePost(postDTO);
        return ResponseEntity.ok(updatePost);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDTO>> getUserPosts(@PathVariable Long userId) {
        return ResponseEntity.ok(postService.getUserPosts(userId));
    }


    @PostMapping("/user/wished/post/{postId}")
    public ResponseEntity<?> addWishedList(@PathVariable Long postId) {
        postService.addToWishList(postId);
        return ResponseEntity.ok(null);
    }


    @PostMapping("/user/blacked/post/{postId}")
    public ResponseEntity<?> addBlackedList(@PathVariable Long postId) {
        postService.addToBlackList(postId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/user/wished/post")
    public ResponseEntity<List<PostDTO>> getAllWishedPosts() {
        return ResponseEntity.ok(postService.getAllWishedPosts());
    }

    @GetMapping("/user/blacked/post")
    public ResponseEntity<List<PostDTO>> getAllBlackedPosts() {
        return ResponseEntity.ok(postService.getAllBlackedPosts());
    }

    @DeleteMapping("/user/wished/post/{postId}")
    public ResponseEntity<?> removeFromWishList(@PathVariable Long postId) {
        postService.removeFromWishList(postId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/user/blacked/post/{postId}")
    public ResponseEntity<?> removeFromBlackList(@PathVariable Long postId) {
        postService.removeFromBlackList(postId);
        return ResponseEntity.ok(null);
    }


    @PostMapping("/user/post/{postId}/comment")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        commentDTO.setPostId(postId);
        CommentDTO comment = commentService.createComment(commentDTO);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/user/post/comment/{commentId}/like")
    public ResponseEntity<CommentDTO> likeComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.likeComment(commentId));
    }

    @PostMapping("/user/post/comment/{commentId}/dislike")
    public ResponseEntity<CommentDTO> dislikeComment(@PathVariable Long commentId) {
        return ResponseEntity.ok(commentService.dislikeComment(commentId));
    }

    @GetMapping("/user/post/{postId}/comment")
    public ResponseEntity<List<CommentDTO>> getAllCommentByPOstId(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getPostComments(postId));
    }


}
