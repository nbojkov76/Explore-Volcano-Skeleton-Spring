package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Long> {

    Optional<Volcano> findByName(String name);
@Query("select v From Volcano v WHERE v.elevation > 3000 AND v.lastEruption is not null and v.active is true ORDER BY v.elevation Desc")
    List<Volcano> findAllByNameAndLocationOver3000();
}
