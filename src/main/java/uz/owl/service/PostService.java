package uz.owl.service;

import javafx.geometry.Pos;
import org.springframework.stereotype.Service;
import uz.owl.domain.Avatar;
import uz.owl.domain.Post;
import uz.owl.domain.User;
import uz.owl.repository.AvatarRepository;
import uz.owl.repository.PostRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.dto.PostDTO;
import uz.owl.service.dto.UserDTO;
import uz.owl.service.mapper.PostMapper;
import uz.owl.service.mapper.UserMapper;
import uz.owl.web.rest.errors.NotFoundExceptions;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service

public class PostService {

    private PostRepository postRepository;

    private PostMapper postMapper;

    private UserRepository userRepository;

    private UserMapper userMapper;

    private AvatarRepository avatarRepository;

    public PostService(PostRepository postRepository, PostMapper postMapper, UserRepository userRepository, UserMapper userMapper, AvatarRepository avatarRepository) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.avatarRepository = avatarRepository;
    }


    @Transactional
    public PostDTO getPostById(Long id) {

        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            throw new NotFoundExceptions("Post not found");
        }
        PostDTO postDTO = postMapper.toPostDTO(post);

        return postDTO;
    }

    @Transactional
    public List<PostDTO> getPostBySortType(PostSortType postSortType) {

        User sessionUSer = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        Set<Post> blackedPosts = sessionUSer.getNotInterestedPosts();

        Predicate<Post> postFilter = o -> !blackedPosts.contains(o);

        switch (postSortType) {
            case mostPopular:
                return postRepository.getMostPopular().stream().filter(postFilter).map(post -> postMapper.toPostDTO(post)).collect(Collectors.toList());
            case newest:
                return postRepository.getNewest().stream().filter(postFilter).map(post -> postMapper.toPostDTO(post)).collect(Collectors.toList());
            case subscribed:
                Optional<User> oneByLogin = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get());
                return postRepository.getSubscribed(oneByLogin.get().getId()).stream().filter(postFilter).map(post -> postMapper.toPostDTO(post)).collect(Collectors.toList());
            case topRated:
                return postRepository.getTopRated().stream().filter(postFilter).map(post -> postMapper.toPostDTO(post)).collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

    @Transactional
    public PostDTO crateNewPost(PostDTO postDTO) {
        Post post = new Post();
        post.setBody(postDTO.getBody());
        post.setTitle(postDTO.getTitle());

        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();


        post.setAuthor(user);
        post.setAuthorId(user.getId());

        if (postDTO.getAvatarId() != null) {
            Optional<Avatar> avatarOptional = avatarRepository.findById(postDTO.getAvatarId());
            if (avatarOptional.isPresent()) {
                post.setAvatar(avatarOptional.get());
            } else {
                post.setAvatarId(null);
                post.setAvatar(null);
            }
        }

        post.setLikedCount(0L);
        post.setDislikedCount(0L);
        post.setRegardedCount(0L);

        Post save = postRepository.save(post);


        return postMapper.toPostDTO(save);
    }

    @Transactional
    public PostDTO saveRating(RatingType ratingType, Long postId) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        Post post = postRepository.findById(postId).get();

        switch (ratingType) {
            case like:
                post.getDislikedUsers().remove(user);
                post.getLikedUsers().add(user);
                postRepository.save(post);
                post.setLikedCount(post.getLikedCount());
                post.setDislikedCount(post.getDislikedCount());
                postRepository.save(post);
                break;
            case dislike:
                post.getLikedUsers().remove(user);
                post.getDislikedUsers().add(user);
                postRepository.save(post);
                post.setLikedCount(post.getLikedCount());
                post.setDislikedCount(post.getDislikedCount());
                postRepository.save(post);
                break;
            case regarded:
                post.getRegardedUsers().add(user);
                postRepository.save(post);
                break;
        }
        updateCount(post);

        Post one = postRepository.getOne(postId);
        PostDTO postDTO = postMapper.toPostDTO(one);
        postDTO.setRatingType(ratingType);
        return postDTO;
    }


    @Transactional
    public PostDTO removeRating(RatingType ratingType, Long postId) {
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        Post post = postRepository.findById(postId).get();
        switch (ratingType) {
            case like:

                post.getLikedUsers().remove(user);
                postRepository.save(post);
                break;
            case dislike:

                post.getDislikedUsers().remove(user);
                postRepository.save(post);
                break;
            case regarded:
                post.getRegardedUsers().add(user);
                postRepository.save(post);
                break;
        }

        updateCount(post);


        Post one = postRepository.getOne(postId);


        return postMapper.toPostDTO(one);
    }


    @Transactional
    public PostDTO updatePost(PostDTO postDTO) {
        Post post = postRepository.getOne(postDTO.getId());
        post.setTitle(postDTO.getTitle());
        if (postDTO.getAvatarId() != null) {
            Avatar avatar = avatarRepository.getOne(postDTO.getAvatarId());
            post.setAvatar(avatar);
        }

        post.setBody(postDTO.getBody());

        Post savedPost = postRepository.save(post);
        return postMapper.toPostDTO(savedPost);
    }

    @Transactional
    public void updateCount(Post post) {
        post.setLikedCount((long) post.getLikedUsers().size());
        post.setDislikedCount((long) post.getDislikedUsers().size());
        post.setRegardedCount((long) post.getRegardedUsers().size());
        postRepository.save(post);
    }

    @Transactional
    public List<PostDTO> getUserPosts(Long userId) {
        return postRepository.findAllByAuthorId(userId).stream().map(postMapper::toPostDTO).collect(Collectors.toList());
    }

    @Transactional
    public boolean addToWishList(Long postId) {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        sessionUser.getWishedPosts().add(postRepository.getOne(postId));
        User save = userRepository.save(sessionUser);
        return true;
    }


    @Transactional
    public boolean removeFromWishList(Long postId) {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        sessionUser.getWishedPosts().remove(postRepository.getOne(postId));
        User save = userRepository.save(sessionUser);
        return true;
    }


    @Transactional
    public boolean addToBlackList(Long postId) {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        sessionUser.getNotInterestedPosts().add(postRepository.getOne(postId));
        User save = userRepository.save(sessionUser);
        return true;
    }

    @Transactional
    public boolean removeFromBlackList(Long postId) {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
        sessionUser.getNotInterestedPosts().remove(postRepository.getOne(postId));
        User save = userRepository.save(sessionUser);
        return true;
    }

    @Transactional
    public List<PostDTO> getAllWishedPosts() {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        List<PostDTO> collect = sessionUser.getWishedPosts().stream().map(postMapper::toPostDTO).collect(Collectors.toList());

        return collect;
    }


    @Transactional
    public List<PostDTO> getAllBlackedPosts() {
        User sessionUser = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        List<PostDTO> collect = sessionUser.getNotInterestedPosts().stream().map(postMapper::toPostDTO).collect(Collectors.toList());

        return collect;
    }

}
