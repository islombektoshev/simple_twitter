package uz.owl.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.owl.domain.Post;
import uz.owl.service.dto.PostDTO;

@Service
public class PostMapper {

    @Transactional
    public PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setDislikedCount(post.getDislikedCount());
        postDTO.setId(post.getId());
        postDTO.setImageUrl(post.getImageUrl());
        postDTO.setLikedCount(post.getLikedCount());
        postDTO.setRegardedCount(post.getRegardedCount());
        postDTO.setTitle(post.getTitle());
        return postDTO;
    }

}
