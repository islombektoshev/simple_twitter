package uz.owl.repository;

import uz.owl.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findByName(@NotNull @Size(max = 50) String name);

}
