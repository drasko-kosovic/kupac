package kupac.service;

import kupac.domain.Viewkorpa;
import kupac.repository.ViewkorpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Viewkorpa}.
 */
@Service
@Transactional
public class ViewkorpaService {

    private final Logger log = LoggerFactory.getLogger(ViewkorpaService.class);

    private final ViewkorpaRepository viewkorpaRepository;

    public ViewkorpaService(ViewkorpaRepository viewkorpaRepository) {
        this.viewkorpaRepository = viewkorpaRepository;
    }

    /**
     * Save a viewkorpa.
     *
     * @param viewkorpa the entity to save.
     * @return the persisted entity.
     */
    public Viewkorpa save(Viewkorpa viewkorpa) {
        log.debug("Request to save Viewkorpa : {}", viewkorpa);
        return viewkorpaRepository.save(viewkorpa);
    }

    /**
     * Get all the viewkorpas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Viewkorpa> findAll(Pageable pageable) {
        log.debug("Request to get all Viewkorpas");
        return viewkorpaRepository.findAll(pageable);
    }


    /**
     * Get one viewkorpa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Viewkorpa> findOne(Long id) {
        log.debug("Request to get Viewkorpa : {}", id);
        return viewkorpaRepository.findById(id);
    }

    /**
     * Delete the viewkorpa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Viewkorpa : {}", id);
        viewkorpaRepository.deleteById(id);
    }
}
