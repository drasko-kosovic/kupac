package kupac.web.rest;

import kupac.domain.Viewkorpa;
import kupac.service.ViewkorpaService;
import kupac.web.rest.errors.BadRequestAlertException;
import kupac.service.dto.ViewkorpaCriteria;
import kupac.service.ViewkorpaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kupac.domain.Viewkorpa}.
 */
@RestController
@RequestMapping("/api")
public class ViewkorpaResource {

    private final Logger log = LoggerFactory.getLogger(ViewkorpaResource.class);

    private final ViewkorpaService viewkorpaService;

    private final ViewkorpaQueryService viewkorpaQueryService;

    public ViewkorpaResource(ViewkorpaService viewkorpaService, ViewkorpaQueryService viewkorpaQueryService) {
        this.viewkorpaService = viewkorpaService;
        this.viewkorpaQueryService = viewkorpaQueryService;
    }

    /**
     * {@code GET  /viewkorpas} : get all the viewkorpas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of viewkorpas in body.
     */
    @GetMapping("/viewkorpas")
    public ResponseEntity<List<Viewkorpa>> getAllViewkorpas(ViewkorpaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Viewkorpas by criteria: {}", criteria);
        Page<Viewkorpa> page = viewkorpaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /viewkorpas/count} : count all the viewkorpas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/viewkorpas/count")
    public ResponseEntity<Long> countViewkorpas(ViewkorpaCriteria criteria) {
        log.debug("REST request to count Viewkorpas by criteria: {}", criteria);
        return ResponseEntity.ok().body(viewkorpaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /viewkorpas/:id} : get the "id" viewkorpa.
     *
     * @param id the id of the viewkorpa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the viewkorpa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/viewkorpas/{id}")
    public ResponseEntity<Viewkorpa> getViewkorpa(@PathVariable Long id) {
        log.debug("REST request to get Viewkorpa : {}", id);
        Optional<Viewkorpa> viewkorpa = viewkorpaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(viewkorpa);
    }
}
