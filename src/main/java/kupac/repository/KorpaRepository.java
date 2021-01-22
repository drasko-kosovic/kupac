package kupac.repository;

import kupac.domain.Korpa;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface KorpaRepository extends JpaRepository<Korpa, Long>, JpaSpecificationExecutor<Korpa> {
    List<Korpa> findByArtikal(String artikal);

    @Query(value = "select * from korpa where artikal=:artikal and cijena=:cijena", nativeQuery = true)
    List<Korpa> findByArikalCijena(String artikal, Double cijena);

    // List<Korpa> findByCijena(Double cijena);
}
