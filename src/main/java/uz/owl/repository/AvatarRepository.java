package uz.owl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.owl.domain.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
