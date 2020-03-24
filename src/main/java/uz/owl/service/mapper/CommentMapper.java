package uz.owl.service.mapper;

import org.springframework.stereotype.Service;
import uz.owl.domain.Comment;
import uz.owl.domain.User;
import uz.owl.repository.CommentRepository;
import uz.owl.repository.PostRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.dto.CommentDTO;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentMapper {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public CommentMapper(CommentRepository commentRepository, PostRepository postRepository, PostMapper postMapper, UserMapper userMapper, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postMapper = postMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    private User getSessionUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
    }

    @Transactional
    public CommentDTO toDTO(Comment comment) {
        if (comment == null) return null;
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthorDTO(userMapper.toUserDTO(comment.getAuthor()));
        commentDTO.setBody(comment.getBody());
        commentDTO.setId(comment.getId());
        commentDTO.setPostId(comment.getPostId());

//        if (comment.getRepliedComment() != null) {
////            commentDTO.setRepliedTo(toDTO(comment.getRepliedComment()));
////        }

        List<Comment> byRepliedCommentId = commentRepository.findAllByRepliedCommentId(comment.getId());
        if (!byRepliedCommentId.isEmpty()) {
            Set<CommentDTO> result = new HashSet<>();
            byRepliedCommentId.forEach(repliedComment -> {
                CommentDTO toDTO = toDTO(repliedComment);
                result.add(toDTO);
            });
            commentDTO.setReplies(result);
        } else {
            commentDTO.setReplies(new HashSet<>());
        }

        commentDTO.setDeviceUserLiked(comment.getLikedUser().contains(getSessionUser()));
        commentDTO.setDeviceUserDisliked(comment.getDislikedUser().contains(getSessionUser()));

        commentDTO.setLikeCount((long) comment.getLikedUser().size());
        commentDTO.setDislikeCount((long) comment.getDislikedUser().size());

        return commentDTO;

    }

}
