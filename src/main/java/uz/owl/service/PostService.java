package uz.owl.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import uz.owl.domain.Post;
import uz.owl.domain.User;
import uz.owl.repository.PostRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.dto.PostDTO;
import uz.owl.service.mapper.PostMapper;
import uz.owl.web.rest.errors.NotFoundExceptions;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;

    private PostMapper postMapper;

    private UserRepository userRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
    }


    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new NotFoundExceptions("Post not found");
        }
        return postMapper.toPostDTO(post);
    }

    public List<PostDTO> getPostBySortType(PostSortType postSortType) {
        switch (postSortType) {
            case mostPopular:
                return postRepository.getMostPopular().stream().map(Post::toDTO).collect(Collectors.toList());
            case newest:
                return postRepository.getNewest().stream().map(Post::toDTO).collect(Collectors.toList());
            case subscribed:
                Optional<User> oneByLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
                return postRepository.getSubscribed(oneByLogin.get().getId()).stream().map(Post::toDTO).collect(Collectors.toList());
            case topRated:
                return postRepository.getTopRated().stream().map(Post::toDTO).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    public PostDTO crateNewPost(PostDTO postDTO) {
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setImageUrl(postDTO.getImageUrl());
        post.setTitle(postDTO.getTitle());

        Post save = postRepository.save(post);

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        user.getPosts().add(save);

        userRepository.save(user);
        return save.toDTO();
    }

    public PostDTO saveRating(RatingType ratingType, Long postId) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        Post post = postRepository.findById(postId).get();
        switch (ratingType) {
            case like:
                post.getDislikedUsers().remove(user);
                post.getLikedUsers().add(user);
                postRepository.saveAndFlush(post);
                post.setLikedCount(post.getLikedCount());
                post.setDislikedCount(post.getDislikedCount());
                postRepository.saveAndFlush(post);
                break;
            case dislike:
                post.getLikedUsers().remove(user);
                post.getDislikedUsers().add(user);
                postRepository.saveAndFlush(post);
                post.setLikedCount(post.getLikedCount());
                post.setDislikedCount(post.getDislikedCount());
                postRepository.saveAndFlush(post);
                break;
            case regarded:
                post.getRegardedUsers().add(user);
                postRepository.saveAndFlush(post);
                break;
        }
        return postRepository.getOne(postId).toDTO();
    }

    public PostDTO updatePost(PostDTO postDTO) {
        Post post = postRepository.getOne(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        post.setImageUrl(postDTO.getImageUrl());
        post.setBody(postDTO.getBody());

        Post savedPost = postRepository.saveAndFlush(post);
        return savedPost.toDTO();
    }
}
