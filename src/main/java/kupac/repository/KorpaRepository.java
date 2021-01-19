package kupac.repository;

import kupac.domain.Korpa;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Korpa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KorpaRepository extends JpaRepository<Korpa, Long>, JpaSpecificationExecutor<Korpa> {

    List<Korpa> findByArtikal(String artikal);
}
