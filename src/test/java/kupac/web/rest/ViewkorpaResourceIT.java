package kupac.web.rest;

import kupac.KupacApp;
import kupac.domain.Viewkorpa;
import kupac.repository.ViewkorpaRepository;
import kupac.service.ViewkorpaService;
import kupac.service.dto.ViewkorpaCriteria;
import kupac.service.ViewkorpaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ViewkorpaResource} REST controller.
 */
@SpringBootTest(classes = KupacApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ViewkorpaResourceIT {

    private static final String DEFAULT_ARTIKAL = "AAAAAAAAAA";
    private static final String UPDATED_ARTIKAL = "BBBBBBBBBB";

    private static final Double DEFAULT_CIJENA = 1D;
    private static final Double UPDATED_CIJENA = 2D;
    private static final Double SMALLER_CIJENA = 1D - 1D;

    private static final Double DEFAULT_UKUPNO = 1D;
    private static final Double UPDATED_UKUPNO = 2D;
    private static final Double SMALLER_UKUPNO = 1D - 1D;

    @Autowired
    private ViewkorpaRepository viewkorpaRepository;

    @Autowired
    private ViewkorpaService viewkorpaService;

    @Autowired
    private ViewkorpaQueryService viewkorpaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restViewkorpaMockMvc;

    private Viewkorpa viewkorpa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viewkorpa createEntity(EntityManager em) {
        Viewkorpa viewkorpa = new Viewkorpa()
            .artikal(DEFAULT_ARTIKAL)
            .cijena(DEFAULT_CIJENA)
            .ukupno(DEFAULT_UKUPNO);
        return viewkorpa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Viewkorpa createUpdatedEntity(EntityManager em) {
        Viewkorpa viewkorpa = new Viewkorpa()
            .artikal(UPDATED_ARTIKAL)
            .cijena(UPDATED_CIJENA)
            .ukupno(UPDATED_UKUPNO);
        return viewkorpa;
    }

    @BeforeEach
    public void initTest() {
        viewkorpa = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllViewkorpas() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList
        restViewkorpaMockMvc.perform(get("/api/viewkorpas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewkorpa.getId().intValue())))
            .andExpect(jsonPath("$.[*].artikal").value(hasItem(DEFAULT_ARTIKAL)))
            .andExpect(jsonPath("$.[*].cijena").value(hasItem(DEFAULT_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].ukupno").value(hasItem(DEFAULT_UKUPNO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getViewkorpa() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get the viewkorpa
        restViewkorpaMockMvc.perform(get("/api/viewkorpas/{id}", viewkorpa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(viewkorpa.getId().intValue()))
            .andExpect(jsonPath("$.artikal").value(DEFAULT_ARTIKAL))
            .andExpect(jsonPath("$.cijena").value(DEFAULT_CIJENA.doubleValue()))
            .andExpect(jsonPath("$.ukupno").value(DEFAULT_UKUPNO.doubleValue()));
    }


    @Test
    @Transactional
    public void getViewkorpasByIdFiltering() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        Long id = viewkorpa.getId();

        defaultViewkorpaShouldBeFound("id.equals=" + id);
        defaultViewkorpaShouldNotBeFound("id.notEquals=" + id);

        defaultViewkorpaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultViewkorpaShouldNotBeFound("id.greaterThan=" + id);

        defaultViewkorpaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultViewkorpaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllViewkorpasByArtikalIsEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal equals to DEFAULT_ARTIKAL
        defaultViewkorpaShouldBeFound("artikal.equals=" + DEFAULT_ARTIKAL);

        // Get all the viewkorpaList where artikal equals to UPDATED_ARTIKAL
        defaultViewkorpaShouldNotBeFound("artikal.equals=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByArtikalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal not equals to DEFAULT_ARTIKAL
        defaultViewkorpaShouldNotBeFound("artikal.notEquals=" + DEFAULT_ARTIKAL);

        // Get all the viewkorpaList where artikal not equals to UPDATED_ARTIKAL
        defaultViewkorpaShouldBeFound("artikal.notEquals=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByArtikalIsInShouldWork() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal in DEFAULT_ARTIKAL or UPDATED_ARTIKAL
        defaultViewkorpaShouldBeFound("artikal.in=" + DEFAULT_ARTIKAL + "," + UPDATED_ARTIKAL);

        // Get all the viewkorpaList where artikal equals to UPDATED_ARTIKAL
        defaultViewkorpaShouldNotBeFound("artikal.in=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByArtikalIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal is not null
        defaultViewkorpaShouldBeFound("artikal.specified=true");

        // Get all the viewkorpaList where artikal is null
        defaultViewkorpaShouldNotBeFound("artikal.specified=false");
    }
                @Test
    @Transactional
    public void getAllViewkorpasByArtikalContainsSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal contains DEFAULT_ARTIKAL
        defaultViewkorpaShouldBeFound("artikal.contains=" + DEFAULT_ARTIKAL);

        // Get all the viewkorpaList where artikal contains UPDATED_ARTIKAL
        defaultViewkorpaShouldNotBeFound("artikal.contains=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByArtikalNotContainsSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where artikal does not contain DEFAULT_ARTIKAL
        defaultViewkorpaShouldNotBeFound("artikal.doesNotContain=" + DEFAULT_ARTIKAL);

        // Get all the viewkorpaList where artikal does not contain UPDATED_ARTIKAL
        defaultViewkorpaShouldBeFound("artikal.doesNotContain=" + UPDATED_ARTIKAL);
    }


    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena equals to DEFAULT_CIJENA
        defaultViewkorpaShouldBeFound("cijena.equals=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena equals to UPDATED_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.equals=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena not equals to DEFAULT_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.notEquals=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena not equals to UPDATED_CIJENA
        defaultViewkorpaShouldBeFound("cijena.notEquals=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena in DEFAULT_CIJENA or UPDATED_CIJENA
        defaultViewkorpaShouldBeFound("cijena.in=" + DEFAULT_CIJENA + "," + UPDATED_CIJENA);

        // Get all the viewkorpaList where cijena equals to UPDATED_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.in=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena is not null
        defaultViewkorpaShouldBeFound("cijena.specified=true");

        // Get all the viewkorpaList where cijena is null
        defaultViewkorpaShouldNotBeFound("cijena.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena is greater than or equal to DEFAULT_CIJENA
        defaultViewkorpaShouldBeFound("cijena.greaterThanOrEqual=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena is greater than or equal to UPDATED_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.greaterThanOrEqual=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena is less than or equal to DEFAULT_CIJENA
        defaultViewkorpaShouldBeFound("cijena.lessThanOrEqual=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena is less than or equal to SMALLER_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.lessThanOrEqual=" + SMALLER_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena is less than DEFAULT_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.lessThan=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena is less than UPDATED_CIJENA
        defaultViewkorpaShouldBeFound("cijena.lessThan=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where cijena is greater than DEFAULT_CIJENA
        defaultViewkorpaShouldNotBeFound("cijena.greaterThan=" + DEFAULT_CIJENA);

        // Get all the viewkorpaList where cijena is greater than SMALLER_CIJENA
        defaultViewkorpaShouldBeFound("cijena.greaterThan=" + SMALLER_CIJENA);
    }


    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno equals to DEFAULT_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.equals=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno equals to UPDATED_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.equals=" + UPDATED_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno not equals to DEFAULT_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.notEquals=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno not equals to UPDATED_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.notEquals=" + UPDATED_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsInShouldWork() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno in DEFAULT_UKUPNO or UPDATED_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.in=" + DEFAULT_UKUPNO + "," + UPDATED_UKUPNO);

        // Get all the viewkorpaList where ukupno equals to UPDATED_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.in=" + UPDATED_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno is not null
        defaultViewkorpaShouldBeFound("ukupno.specified=true");

        // Get all the viewkorpaList where ukupno is null
        defaultViewkorpaShouldNotBeFound("ukupno.specified=false");
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno is greater than or equal to DEFAULT_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.greaterThanOrEqual=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno is greater than or equal to UPDATED_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.greaterThanOrEqual=" + UPDATED_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno is less than or equal to DEFAULT_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.lessThanOrEqual=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno is less than or equal to SMALLER_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.lessThanOrEqual=" + SMALLER_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsLessThanSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno is less than DEFAULT_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.lessThan=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno is less than UPDATED_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.lessThan=" + UPDATED_UKUPNO);
    }

    @Test
    @Transactional
    public void getAllViewkorpasByUkupnoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        viewkorpaRepository.saveAndFlush(viewkorpa);

        // Get all the viewkorpaList where ukupno is greater than DEFAULT_UKUPNO
        defaultViewkorpaShouldNotBeFound("ukupno.greaterThan=" + DEFAULT_UKUPNO);

        // Get all the viewkorpaList where ukupno is greater than SMALLER_UKUPNO
        defaultViewkorpaShouldBeFound("ukupno.greaterThan=" + SMALLER_UKUPNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultViewkorpaShouldBeFound(String filter) throws Exception {
        restViewkorpaMockMvc.perform(get("/api/viewkorpas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(viewkorpa.getId().intValue())))
            .andExpect(jsonPath("$.[*].artikal").value(hasItem(DEFAULT_ARTIKAL)))
            .andExpect(jsonPath("$.[*].cijena").value(hasItem(DEFAULT_CIJENA.doubleValue())))
            .andExpect(jsonPath("$.[*].ukupno").value(hasItem(DEFAULT_UKUPNO.doubleValue())));

        // Check, that the count call also returns 1
        restViewkorpaMockMvc.perform(get("/api/viewkorpas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultViewkorpaShouldNotBeFound(String filter) throws Exception {
        restViewkorpaMockMvc.perform(get("/api/viewkorpas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restViewkorpaMockMvc.perform(get("/api/viewkorpas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingViewkorpa() throws Exception {
        // Get the viewkorpa
        restViewkorpaMockMvc.perform(get("/api/viewkorpas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
