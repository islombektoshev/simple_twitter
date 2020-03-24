package uz.owl.service.mapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.owl.domain.Post;
import uz.owl.domain.User;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.RatingType;
import uz.owl.service.dto.PostDTO;
import uz.owl.service.dto.UserDTO;

@Service
public class PostMapper {


    private UserRepository userRepository;

    private UserMapper userMapper;

    public PostMapper(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public PostDTO toPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setBody(post.getBody());
        postDTO.setDislikedCount(post.getDislikedCount());
        postDTO.setId(post.getId());
        postDTO.setLikedCount(post.getLikedCount());
        postDTO.setRegardedCount(post.getRegardedCount());
        postDTO.setTitle(post.getTitle());
        postDTO.setAvatarId(post.getAvatar() != null ? post.getAvatar().getAvatarId() : null);
        User one = userRepository.getOne(post.getAuthorId());
        postDTO.setAuthor(userMapper.userToUserDTO(one));
        User user = userRepository.getOne(postDTO.getAuthor().getId());
        UserDTO userDTO = userMapper.toUserDTO(user);
        postDTO.setAuthor(userDTO);

        User sessionUSer = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();

        postDTO.setRegarded(false);
        post.getRegardedUsers().forEach(user1 -> {
            if (user1.getId().equals(sessionUSer.getId())) {
                postDTO.setRegarded(true);
            }
        });

        post.getLikedUsers().forEach(user1 -> {
            if (user1.getId().equals(sessionUSer.getId())) {
                postDTO.setRatingType(RatingType.like);
            }
        });

        if (postDTO.getRatingType() == null) {
            post.getDislikedUsers().forEach(user1 -> {
                if (user1.getId().equals(sessionUSer.getId())) {
                    postDTO.setRatingType(RatingType.dislike);
                }
            });

        }


        return postDTO;
    }

}
