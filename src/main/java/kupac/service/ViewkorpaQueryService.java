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

import kupac.domain.Viewkorpa;
import kupac.domain.*; // for static metamodels
import kupac.repository.ViewkorpaRepository;
import kupac.service.dto.ViewkorpaCriteria;

/**
 * Service for executing complex queries for {@link Viewkorpa} entities in the database.
 * The main input is a {@link ViewkorpaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Viewkorpa} or a {@link Page} of {@link Viewkorpa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ViewkorpaQueryService extends QueryService<Viewkorpa> {

    private final Logger log = LoggerFactory.getLogger(ViewkorpaQueryService.class);

    private final ViewkorpaRepository viewkorpaRepository;

    public ViewkorpaQueryService(ViewkorpaRepository viewkorpaRepository) {
        this.viewkorpaRepository = viewkorpaRepository;
    }

    /**
     * Return a {@link List} of {@link Viewkorpa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Viewkorpa> findByCriteria(ViewkorpaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Viewkorpa> specification = createSpecification(criteria);
        return viewkorpaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Viewkorpa} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Viewkorpa> findByCriteria(ViewkorpaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Viewkorpa> specification = createSpecification(criteria);
        return viewkorpaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ViewkorpaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Viewkorpa> specification = createSpecification(criteria);
        return viewkorpaRepository.count(specification);
    }

    /**
     * Function to convert {@link ViewkorpaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Viewkorpa> createSpecification(ViewkorpaCriteria criteria) {
        Specification<Viewkorpa> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Viewkorpa_.id));
            }
            if (criteria.getArtikal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArtikal(), Viewkorpa_.artikal));
            }
            if (criteria.getCijena() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCijena(), Viewkorpa_.cijena));
            }
            if (criteria.getUkupno() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUkupno(), Viewkorpa_.ukupno));
            }
        }
        return specification;
    }
}
