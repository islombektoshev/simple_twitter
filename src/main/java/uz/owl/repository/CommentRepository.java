package uz.owl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.owl.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByRepliedCommentId(Long repliedCommentId);

    List<Comment> findAllByPostIdAndRepliedCommentId(Long postId, Long repliedCommentId);
}
