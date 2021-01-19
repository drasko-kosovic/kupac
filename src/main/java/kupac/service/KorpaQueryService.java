package kupac.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import kupac.domain.Korpa;
import kupac.domain.*; // for static metamodels
import kupac.repository.KorpaRepository;
import kupac.service.dto.KorpaCriteria;

/**
 * Service for executing complex queries for {@link Korpa} entities in the database.
 * The main input is a {@link KorpaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Korpa} or a {@link Page} of {@link Korpa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class KorpaQueryService extends QueryService<Korpa> {

    private final Logger log = LoggerFactory.getLogger(KorpaQueryService.class);

    private final KorpaRepository korpaRepository;

    public KorpaQueryService(KorpaRepository korpaRepository) {
        this.korpaRepository = korpaRepository;
    }

    /**
     * Return a {@link List} of {@link Korpa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Korpa> findByCriteria(KorpaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Korpa> specification = createSpecification(criteria);
        return korpaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Korpa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Korpa> findByCriteria(KorpaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Korpa> specification = createSpecification(criteria);
        return korpaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(KorpaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Korpa> specification = createSpecification(criteria);
        return korpaRepository.count(specification);
    }

    /**
     * Function to convert {@link KorpaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Korpa> createSpecification(KorpaCriteria criteria) {
        Specification<Korpa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Korpa_.id));
            }
            if (criteria.getArtikal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArtikal(), Korpa_.artikal));
            }
            if (criteria.getCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCijena(), Korpa_.cijena));
            }
            if (criteria.getIzaberi() != null) {
                specification = specification.and(buildSpecification(criteria.getIzaberi(), Korpa_.izaberi));
            }
        }
        return specification;
    }
}
