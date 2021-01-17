package kupac.web.rest;

import kupac.domain.Korpa;
import kupac.service.KorpaService;
import kupac.web.rest.errors.BadRequestAlertException;
import kupac.service.dto.KorpaCriteria;
import kupac.service.ExcelService;
import kupac.service.KorpaQueryService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link kupac.domain.Korpa}.
 */
@RestController
@RequestMapping("/api")
public class KorpaResource {
    @Autowired
    ExcelService fileService;
    private final Logger log = LoggerFactory.getLogger(KorpaResource.class);

    private static final String ENTITY_NAME = "korpa";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KorpaService korpaService;

    private final KorpaQueryService korpaQueryService;

    public KorpaResource(KorpaService korpaService, KorpaQueryService korpaQueryService) {
        this.korpaService = korpaService;
        this.korpaQueryService = korpaQueryService;
    }

    /**
     * {@code POST  /korpas} : Create a new korpa.
     *
     * @param korpa the korpa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new korpa, or with status {@code 400 (Bad Request)} if the korpa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/korpas")
    public ResponseEntity<Korpa> createKorpa(@Valid @RequestBody Korpa korpa) throws URISyntaxException {
        log.debug("REST request to save Korpa : {}", korpa);
        if (korpa.getId() != null) {
            throw new BadRequestAlertException("A new korpa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Korpa result = korpaService.save(korpa);
        return ResponseEntity.created(new URI("/api/korpas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /korpas} : Updates an existing korpa.
     *
     * @param korpa the korpa to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated korpa,
     * or with status {@code 400 (Bad Request)} if the korpa is not valid,
     * or with status {@code 500 (Internal Server Error)} if the korpa couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/korpas")
    public ResponseEntity<Korpa> updateKorpa(@Valid @RequestBody Korpa korpa) throws URISyntaxException {
        log.debug("REST request to update Korpa : {}", korpa);
        if (korpa.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Korpa result = korpaService.save(korpa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, korpa.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /korpas} : get all the korpas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of korpas in body.
     */
    @GetMapping("/korpas")
    public ResponseEntity<List<Korpa>> getAllKorpas(KorpaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Korpas by criteria: {}", criteria);
        Page<Korpa> page = korpaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /korpas/count} : count all the korpas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/korpas/count")
    public ResponseEntity<Long> countKorpas(KorpaCriteria criteria) {
        log.debug("REST request to count Korpas by criteria: {}", criteria);
        return ResponseEntity.ok().body(korpaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /korpas/:id} : get the "id" korpa.
     *
     * @param id the id of the korpa to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the korpa, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/korpas/{id}")
    public ResponseEntity<Korpa> getKorpa(@PathVariable Long id) {
        log.debug("REST request to get Korpa : {}", id);
        Optional<Korpa> korpa = korpaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(korpa);
    }

    /**
     * {@code DELETE  /korpas/:id} : delete the "id" korpa.
     *
     * @param id the id of the korpa to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/korpas/{id}")
    public ResponseEntity<Void> deleteKorpa(@PathVariable Long id) {
        log.debug("REST request to delete Korpa : {}", id);
        korpaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/download")
  public ResponseEntity<Resource> getFile() {
    String filename = "tutorials.xlsx";
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
  }
}
