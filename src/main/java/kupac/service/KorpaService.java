package kupac.service;

import kupac.domain.Korpa;
import kupac.repository.KorpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Korpa}.
 */
@Service
@Transactional
public class KorpaService {

    private final Logger log = LoggerFactory.getLogger(KorpaService.class);

    private final KorpaRepository korpaRepository;

    public KorpaService(KorpaRepository korpaRepository) {
        this.korpaRepository = korpaRepository;
    }

    /**
     * Save a korpa.
     *
     * @param korpa the entity to save.
     * @return the persisted entity.
     */
    public Korpa save(Korpa korpa) {
        log.debug("Request to save Korpa : {}", korpa);
        return korpaRepository.save(korpa);
    }

    /**
     * Get all the korpas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Korpa> findAll(Pageable pageable) {
        log.debug("Request to get all Korpas");
        return korpaRepository.findAll(pageable);
    }


    /**
     * Get one korpa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Korpa> findOne(Long id) {
        log.debug("Request to get Korpa : {}", id);
        return korpaRepository.findById(id);
    }

    /**
     * Delete the korpa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Korpa : {}", id);
        korpaRepository.deleteById(id);
    }
}
