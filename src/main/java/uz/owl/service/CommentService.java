package uz.owl.service;

import org.springframework.stereotype.Service;
import uz.owl.domain.Comment;
import uz.owl.domain.Post;
import uz.owl.domain.User;
import uz.owl.repository.CommentRepository;
import uz.owl.repository.PostRepository;
import uz.owl.repository.UserRepository;
import uz.owl.security.SecurityUtils;
import uz.owl.service.dto.CommentDTO;
import uz.owl.service.mapper.CommentMapper;
import uz.owl.service.mapper.PostMapper;
import uz.owl.service.mapper.UserMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    public CommentService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository, UserMapper userMapper, PostMapper postMapper, CommentMapper commentMapper) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
    }

    @Transactional
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setAuthor(getSessionUser());
        comment.setAuthorId(getSessionUser().getId());
        comment.setBody(commentDTO.getBody());
        Post post = postRepository.getOne(commentDTO.getPostId());
        comment.setPost(post);
        comment.setPostId(post.getId());
        if (commentDTO.getRepliedTo() != null) {
            Comment repliedComment = commentRepository.getOne(commentDTO.getRepliedTo().getId());
            comment.setRepliedCommentId(repliedComment.getId());
            comment.setRepliedComment(repliedComment);
        }

        Comment save = commentRepository.save(comment);
        return commentMapper.toDTO(save);
    }

    @Transactional
    public List<CommentDTO> getPostComments(Long postId) {
        List<Comment> allByPostId = commentRepository.findAllByPostIdAndRepliedCommentId(postId, null);
        return allByPostId.stream().map(commentMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO likeComment(Long commentId) {
        Comment comment = commentRepository.getOne(commentId);
        Set<User> likedUser = comment.getLikedUser();
        if (likedUser.contains(getSessionUser())) {
            likedUser.remove(getSessionUser());
        } else {
            likedUser.add(getSessionUser());
            comment.getDislikedUser().remove(getSessionUser());
        }
        return commentMapper.toDTO(commentRepository.save(comment));
    }

    @Transactional
    public CommentDTO dislikeComment(Long commentId) {
        Comment comment = commentRepository.getOne(commentId);
        Set<User> dislikedUser = comment.getDislikedUser();
        if (dislikedUser.contains(getSessionUser())) {
            dislikedUser.remove(getSessionUser());
        } else {
            dislikedUser.add(getSessionUser());
            comment.getLikedUser().remove(getSessionUser());
        }
        return commentMapper.toDTO(commentRepository.save(comment));
    }


    private User getSessionUser() {
        return userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get();
    }
}
