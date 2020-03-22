package uz.owl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.owl.domain.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p order by p.regardedCount desc ")
    List<Post> getMostPopular();

    @Query(value = "select p from Post p order by p.createdDate desc ")
    List<Post> getNewest();

    @Query(nativeQuery = true, value = "select *\n" +
        "from post as p\n" +
        "where p.id in (\n" +
        "    select p2.post_id\n" +
        "    from posts as p2\n" +
        "    where p2.user_id in (\n" +
        "        select f.user_id\n" +
        "        from followers as f\n" +
        "        where f.follower_id = :userId \n" +
        "    )\n" +
        ")\n" +
        "order by p.created_date DESC;")
    List<Post> getSubscribed(Long userId);

    @Query(value = "select p from Post p order by p.likedCount desc ")
    List<Post> getTopRated();

    List<Post> findAllByAuthorId(Long authorId);
}
