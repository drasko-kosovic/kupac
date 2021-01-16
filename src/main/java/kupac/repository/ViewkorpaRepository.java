package kupac.repository;

import kupac.domain.Viewkorpa;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Viewkorpa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ViewkorpaRepository extends JpaRepository<Viewkorpa, Long>, JpaSpecificationExecutor<Viewkorpa> {
}
