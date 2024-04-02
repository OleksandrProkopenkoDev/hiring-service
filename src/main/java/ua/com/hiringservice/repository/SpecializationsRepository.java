package ua.com.hiringservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.hiringservice.model.entity.vacancy.Specialization;

/** Repository interface for {@link Specialization} entities. */
public interface SpecializationsRepository extends JpaRepository<Specialization, Long> {

  Optional<Specialization> findByName(String name);
}
