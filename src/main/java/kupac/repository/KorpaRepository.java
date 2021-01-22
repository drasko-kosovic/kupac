package kupac.repository;

import kupac.domain.Korpa;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface KorpaRepository extends JpaRepository<Korpa, Long>, JpaSpecificationExecutor<Korpa> {
    List<Korpa> findByArtikal(String artikal);

    @Query("select k from Korpa k where k.artikal=:artikal and k.cijena=:cijena")
    List<Korpa> findByArikalCijena( @Param("artikal") String artikal,@Param("cijena") Integer cijena );

   List<Korpa> findByCijena(Integer cijena);
}
