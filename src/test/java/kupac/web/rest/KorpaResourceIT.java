package kupac.web.rest;

import kupac.KupacApp;
import kupac.domain.Korpa;
import kupac.repository.KorpaRepository;
import kupac.service.KorpaService;
import kupac.service.dto.KorpaCriteria;
import kupac.service.KorpaQueryService;

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
 * Integration tests for the {@link KorpaResource} REST controller.
 */
@SpringBootTest(classes = KupacApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class KorpaResourceIT {

    private static final String DEFAULT_ARTIKAL = "AAAAAAAAAA";
    private static final String UPDATED_ARTIKAL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CIJENA = 1;
    private static final Integer UPDATED_CIJENA = 2;
    private static final Integer SMALLER_CIJENA = 1 - 1;

    private static final Boolean DEFAULT_IZABERI = false;
    private static final Boolean UPDATED_IZABERI = true;

    @Autowired
    private KorpaRepository korpaRepository;

    @Autowired
    private KorpaService korpaService;

    @Autowired
    private KorpaQueryService korpaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restKorpaMockMvc;

    private Korpa korpa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Korpa createEntity(EntityManager em) {
        Korpa korpa = new Korpa()
            .artikal(DEFAULT_ARTIKAL)
            .cijena(DEFAULT_CIJENA)
            .izaberi(DEFAULT_IZABERI);
        return korpa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Korpa createUpdatedEntity(EntityManager em) {
        Korpa korpa = new Korpa()
            .artikal(UPDATED_ARTIKAL)
            .cijena(UPDATED_CIJENA)
            .izaberi(UPDATED_IZABERI);
        return korpa;
    }

    @BeforeEach
    public void initTest() {
        korpa = createEntity(em);
    }

    @Test
    @Transactional
    public void createKorpa() throws Exception {
        int databaseSizeBeforeCreate = korpaRepository.findAll().size();
        // Create the Korpa
        restKorpaMockMvc.perform(post("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(korpa)))
            .andExpect(status().isCreated());

        // Validate the Korpa in the database
        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeCreate + 1);
        Korpa testKorpa = korpaList.get(korpaList.size() - 1);
        assertThat(testKorpa.getArtikal()).isEqualTo(DEFAULT_ARTIKAL);
        assertThat(testKorpa.getCijena()).isEqualTo(DEFAULT_CIJENA);
        assertThat(testKorpa.isIzaberi()).isEqualTo(DEFAULT_IZABERI);
    }

    @Test
    @Transactional
    public void createKorpaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = korpaRepository.findAll().size();

        // Create the Korpa with an existing ID
        korpa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKorpaMockMvc.perform(post("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(korpa)))
            .andExpect(status().isBadRequest());

        // Validate the Korpa in the database
        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkArtikalIsRequired() throws Exception {
        int databaseSizeBeforeTest = korpaRepository.findAll().size();
        // set the field null
        korpa.setArtikal(null);

        // Create the Korpa, which fails.


        restKorpaMockMvc.perform(post("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(korpa)))
            .andExpect(status().isBadRequest());

        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCijenaIsRequired() throws Exception {
        int databaseSizeBeforeTest = korpaRepository.findAll().size();
        // set the field null
        korpa.setCijena(null);

        // Create the Korpa, which fails.


        restKorpaMockMvc.perform(post("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(korpa)))
            .andExpect(status().isBadRequest());

        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKorpas() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList
        restKorpaMockMvc.perform(get("/api/korpas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(korpa.getId().intValue())))
            .andExpect(jsonPath("$.[*].artikal").value(hasItem(DEFAULT_ARTIKAL)))
            .andExpect(jsonPath("$.[*].cijena").value(hasItem(DEFAULT_CIJENA)))
            .andExpect(jsonPath("$.[*].izaberi").value(hasItem(DEFAULT_IZABERI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getKorpa() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get the korpa
        restKorpaMockMvc.perform(get("/api/korpas/{id}", korpa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(korpa.getId().intValue()))
            .andExpect(jsonPath("$.artikal").value(DEFAULT_ARTIKAL))
            .andExpect(jsonPath("$.cijena").value(DEFAULT_CIJENA))
            .andExpect(jsonPath("$.izaberi").value(DEFAULT_IZABERI.booleanValue()));
    }


    @Test
    @Transactional
    public void getKorpasByIdFiltering() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        Long id = korpa.getId();

        defaultKorpaShouldBeFound("id.equals=" + id);
        defaultKorpaShouldNotBeFound("id.notEquals=" + id);

        defaultKorpaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultKorpaShouldNotBeFound("id.greaterThan=" + id);

        defaultKorpaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultKorpaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllKorpasByArtikalIsEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal equals to DEFAULT_ARTIKAL
        defaultKorpaShouldBeFound("artikal.equals=" + DEFAULT_ARTIKAL);

        // Get all the korpaList where artikal equals to UPDATED_ARTIKAL
        defaultKorpaShouldNotBeFound("artikal.equals=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllKorpasByArtikalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal not equals to DEFAULT_ARTIKAL
        defaultKorpaShouldNotBeFound("artikal.notEquals=" + DEFAULT_ARTIKAL);

        // Get all the korpaList where artikal not equals to UPDATED_ARTIKAL
        defaultKorpaShouldBeFound("artikal.notEquals=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllKorpasByArtikalIsInShouldWork() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal in DEFAULT_ARTIKAL or UPDATED_ARTIKAL
        defaultKorpaShouldBeFound("artikal.in=" + DEFAULT_ARTIKAL + "," + UPDATED_ARTIKAL);

        // Get all the korpaList where artikal equals to UPDATED_ARTIKAL
        defaultKorpaShouldNotBeFound("artikal.in=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllKorpasByArtikalIsNullOrNotNull() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal is not null
        defaultKorpaShouldBeFound("artikal.specified=true");

        // Get all the korpaList where artikal is null
        defaultKorpaShouldNotBeFound("artikal.specified=false");
    }
                @Test
    @Transactional
    public void getAllKorpasByArtikalContainsSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal contains DEFAULT_ARTIKAL
        defaultKorpaShouldBeFound("artikal.contains=" + DEFAULT_ARTIKAL);

        // Get all the korpaList where artikal contains UPDATED_ARTIKAL
        defaultKorpaShouldNotBeFound("artikal.contains=" + UPDATED_ARTIKAL);
    }

    @Test
    @Transactional
    public void getAllKorpasByArtikalNotContainsSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where artikal does not contain DEFAULT_ARTIKAL
        defaultKorpaShouldNotBeFound("artikal.doesNotContain=" + DEFAULT_ARTIKAL);

        // Get all the korpaList where artikal does not contain UPDATED_ARTIKAL
        defaultKorpaShouldBeFound("artikal.doesNotContain=" + UPDATED_ARTIKAL);
    }


    @Test
    @Transactional
    public void getAllKorpasByCijenaIsEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena equals to DEFAULT_CIJENA
        defaultKorpaShouldBeFound("cijena.equals=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena equals to UPDATED_CIJENA
        defaultKorpaShouldNotBeFound("cijena.equals=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena not equals to DEFAULT_CIJENA
        defaultKorpaShouldNotBeFound("cijena.notEquals=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena not equals to UPDATED_CIJENA
        defaultKorpaShouldBeFound("cijena.notEquals=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsInShouldWork() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena in DEFAULT_CIJENA or UPDATED_CIJENA
        defaultKorpaShouldBeFound("cijena.in=" + DEFAULT_CIJENA + "," + UPDATED_CIJENA);

        // Get all the korpaList where cijena equals to UPDATED_CIJENA
        defaultKorpaShouldNotBeFound("cijena.in=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsNullOrNotNull() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena is not null
        defaultKorpaShouldBeFound("cijena.specified=true");

        // Get all the korpaList where cijena is null
        defaultKorpaShouldNotBeFound("cijena.specified=false");
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena is greater than or equal to DEFAULT_CIJENA
        defaultKorpaShouldBeFound("cijena.greaterThanOrEqual=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena is greater than or equal to UPDATED_CIJENA
        defaultKorpaShouldNotBeFound("cijena.greaterThanOrEqual=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena is less than or equal to DEFAULT_CIJENA
        defaultKorpaShouldBeFound("cijena.lessThanOrEqual=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena is less than or equal to SMALLER_CIJENA
        defaultKorpaShouldNotBeFound("cijena.lessThanOrEqual=" + SMALLER_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsLessThanSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena is less than DEFAULT_CIJENA
        defaultKorpaShouldNotBeFound("cijena.lessThan=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena is less than UPDATED_CIJENA
        defaultKorpaShouldBeFound("cijena.lessThan=" + UPDATED_CIJENA);
    }

    @Test
    @Transactional
    public void getAllKorpasByCijenaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where cijena is greater than DEFAULT_CIJENA
        defaultKorpaShouldNotBeFound("cijena.greaterThan=" + DEFAULT_CIJENA);

        // Get all the korpaList where cijena is greater than SMALLER_CIJENA
        defaultKorpaShouldBeFound("cijena.greaterThan=" + SMALLER_CIJENA);
    }


    @Test
    @Transactional
    public void getAllKorpasByIzaberiIsEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where izaberi equals to DEFAULT_IZABERI
        defaultKorpaShouldBeFound("izaberi.equals=" + DEFAULT_IZABERI);

        // Get all the korpaList where izaberi equals to UPDATED_IZABERI
        defaultKorpaShouldNotBeFound("izaberi.equals=" + UPDATED_IZABERI);
    }

    @Test
    @Transactional
    public void getAllKorpasByIzaberiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where izaberi not equals to DEFAULT_IZABERI
        defaultKorpaShouldNotBeFound("izaberi.notEquals=" + DEFAULT_IZABERI);

        // Get all the korpaList where izaberi not equals to UPDATED_IZABERI
        defaultKorpaShouldBeFound("izaberi.notEquals=" + UPDATED_IZABERI);
    }

    @Test
    @Transactional
    public void getAllKorpasByIzaberiIsInShouldWork() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where izaberi in DEFAULT_IZABERI or UPDATED_IZABERI
        defaultKorpaShouldBeFound("izaberi.in=" + DEFAULT_IZABERI + "," + UPDATED_IZABERI);

        // Get all the korpaList where izaberi equals to UPDATED_IZABERI
        defaultKorpaShouldNotBeFound("izaberi.in=" + UPDATED_IZABERI);
    }

    @Test
    @Transactional
    public void getAllKorpasByIzaberiIsNullOrNotNull() throws Exception {
        // Initialize the database
        korpaRepository.saveAndFlush(korpa);

        // Get all the korpaList where izaberi is not null
        defaultKorpaShouldBeFound("izaberi.specified=true");

        // Get all the korpaList where izaberi is null
        defaultKorpaShouldNotBeFound("izaberi.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultKorpaShouldBeFound(String filter) throws Exception {
        restKorpaMockMvc.perform(get("/api/korpas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(korpa.getId().intValue())))
            .andExpect(jsonPath("$.[*].artikal").value(hasItem(DEFAULT_ARTIKAL)))
            .andExpect(jsonPath("$.[*].cijena").value(hasItem(DEFAULT_CIJENA)))
            .andExpect(jsonPath("$.[*].izaberi").value(hasItem(DEFAULT_IZABERI.booleanValue())));

        // Check, that the count call also returns 1
        restKorpaMockMvc.perform(get("/api/korpas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultKorpaShouldNotBeFound(String filter) throws Exception {
        restKorpaMockMvc.perform(get("/api/korpas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restKorpaMockMvc.perform(get("/api/korpas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingKorpa() throws Exception {
        // Get the korpa
        restKorpaMockMvc.perform(get("/api/korpas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKorpa() throws Exception {
        // Initialize the database
        korpaService.save(korpa);

        int databaseSizeBeforeUpdate = korpaRepository.findAll().size();

        // Update the korpa
        Korpa updatedKorpa = korpaRepository.findById(korpa.getId()).get();
        // Disconnect from session so that the updates on updatedKorpa are not directly saved in db
        em.detach(updatedKorpa);
        updatedKorpa
            .artikal(UPDATED_ARTIKAL)
            .cijena(UPDATED_CIJENA)
            .izaberi(UPDATED_IZABERI);

        restKorpaMockMvc.perform(put("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedKorpa)))
            .andExpect(status().isOk());

        // Validate the Korpa in the database
        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeUpdate);
        Korpa testKorpa = korpaList.get(korpaList.size() - 1);
        assertThat(testKorpa.getArtikal()).isEqualTo(UPDATED_ARTIKAL);
        assertThat(testKorpa.getCijena()).isEqualTo(UPDATED_CIJENA);
        assertThat(testKorpa.isIzaberi()).isEqualTo(UPDATED_IZABERI);
    }

    @Test
    @Transactional
    public void updateNonExistingKorpa() throws Exception {
        int databaseSizeBeforeUpdate = korpaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKorpaMockMvc.perform(put("/api/korpas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(korpa)))
            .andExpect(status().isBadRequest());

        // Validate the Korpa in the database
        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKorpa() throws Exception {
        // Initialize the database
        korpaService.save(korpa);

        int databaseSizeBeforeDelete = korpaRepository.findAll().size();

        // Delete the korpa
        restKorpaMockMvc.perform(delete("/api/korpas/{id}", korpa.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Korpa> korpaList = korpaRepository.findAll();
        assertThat(korpaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
