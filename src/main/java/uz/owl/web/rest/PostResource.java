package uz.owl.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.owl.service.PostService;
import uz.owl.service.PostSortType;
import uz.owl.service.RatingType;
import uz.owl.service.dto.PostDTO;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class PostResource {

    private final PostService postService;


    public PostResource(PostService postService) {
        this.postService = postService;
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
    public ResponseEntity<PostDTO> createPost(PostDTO postDTO) {

        return ResponseEntity.ok(postService.crateNewPost(postDTO));
    }

    @PostMapping("/post/{id}/rating")
    public ResponseEntity<PostDTO> rating(RatingType ratingType, @PathVariable Long id) {
        return ResponseEntity.ok(postService.saveRating(ratingType, id));
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<PostDTO> updatePost(PostDTO postDTO, @PathVariable Long id){
        postDTO.setId(id);
        PostDTO updatePost = postService.updatePost(postDTO);
        return ResponseEntity.ok(updatePost);
    }

}
