package inventory.web.rest;

import inventory.InmarApp;

import inventory.domain.Sku;
import inventory.domain.Subcategory;
import inventory.repository.SkuRepository;
import inventory.service.SkuService;
import inventory.service.dto.SkuDTO;
import inventory.service.mapper.SkuMapper;
import inventory.web.rest.errors.ExceptionTranslator;
import inventory.service.dto.SkuCriteria;
import inventory.service.SkuQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static inventory.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SkuResource REST controller.
 *
 * @see SkuResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmarApp.class)
public class SkuResourceIntTest {

    private static final String DEFAULT_SKUID = "AAAAAAAAAA";
    private static final String UPDATED_SKUID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BARCODE = "AAAAAAAAAA";
    private static final String UPDATED_BARCODE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private SkuRepository skuRepository;


    @Autowired
    private SkuMapper skuMapper;
    

    @Autowired
    private SkuService skuService;

    @Autowired
    private SkuQueryService skuQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSkuMockMvc;

    private Sku sku;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SkuResource skuResource = new SkuResource(skuService, skuQueryService);
        this.restSkuMockMvc = MockMvcBuilders.standaloneSetup(skuResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sku createEntity(EntityManager em) {
        Sku sku = new Sku()
            .skuid(DEFAULT_SKUID)
            .name(DEFAULT_NAME)
            .barcode(DEFAULT_BARCODE)
            .status(DEFAULT_STATUS);
        return sku;
    }

    @Before
    public void initTest() {
        sku = createEntity(em);
    }

    @Test
    @Transactional
    public void createSku() throws Exception {
        int databaseSizeBeforeCreate = skuRepository.findAll().size();

        // Create the Sku
        SkuDTO skuDTO = skuMapper.toDto(sku);
        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isCreated());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeCreate + 1);
        Sku testSku = skuList.get(skuList.size() - 1);
        assertThat(testSku.getSkuid()).isEqualTo(DEFAULT_SKUID);
        assertThat(testSku.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSku.getBarcode()).isEqualTo(DEFAULT_BARCODE);
        assertThat(testSku.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSkuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skuRepository.findAll().size();

        // Create the Sku with an existing ID
        sku.setId(1L);
        SkuDTO skuDTO = skuMapper.toDto(sku);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSkuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setSkuid(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.toDto(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setName(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.toDto(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBarcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setBarcode(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.toDto(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = skuRepository.findAll().size();
        // set the field null
        sku.setStatus(null);

        // Create the Sku, which fails.
        SkuDTO skuDTO = skuMapper.toDto(sku);

        restSkuMockMvc.perform(post("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSkus() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList
        restSkuMockMvc.perform(get("/api/skus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sku.getId().intValue())))
            .andExpect(jsonPath("$.[*].skuid").value(hasItem(DEFAULT_SKUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    

    @Test
    @Transactional
    public void getSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", sku.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sku.getId().intValue()))
            .andExpect(jsonPath("$.skuid").value(DEFAULT_SKUID.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.barcode").value(DEFAULT_BARCODE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllSkusBySkuidIsEqualToSomething() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where skuid equals to DEFAULT_SKUID
        defaultSkuShouldBeFound("skuid.equals=" + DEFAULT_SKUID);

        // Get all the skuList where skuid equals to UPDATED_SKUID
        defaultSkuShouldNotBeFound("skuid.equals=" + UPDATED_SKUID);
    }

    @Test
    @Transactional
    public void getAllSkusBySkuidIsInShouldWork() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where skuid in DEFAULT_SKUID or UPDATED_SKUID
        defaultSkuShouldBeFound("skuid.in=" + DEFAULT_SKUID + "," + UPDATED_SKUID);

        // Get all the skuList where skuid equals to UPDATED_SKUID
        defaultSkuShouldNotBeFound("skuid.in=" + UPDATED_SKUID);
    }

    @Test
    @Transactional
    public void getAllSkusBySkuidIsNullOrNotNull() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where skuid is not null
        defaultSkuShouldBeFound("skuid.specified=true");

        // Get all the skuList where skuid is null
        defaultSkuShouldNotBeFound("skuid.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkusByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where name equals to DEFAULT_NAME
        defaultSkuShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the skuList where name equals to UPDATED_NAME
        defaultSkuShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSkusByNameIsInShouldWork() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where name in DEFAULT_NAME or UPDATED_NAME
        defaultSkuShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the skuList where name equals to UPDATED_NAME
        defaultSkuShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllSkusByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where name is not null
        defaultSkuShouldBeFound("name.specified=true");

        // Get all the skuList where name is null
        defaultSkuShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkusByBarcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where barcode equals to DEFAULT_BARCODE
        defaultSkuShouldBeFound("barcode.equals=" + DEFAULT_BARCODE);

        // Get all the skuList where barcode equals to UPDATED_BARCODE
        defaultSkuShouldNotBeFound("barcode.equals=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllSkusByBarcodeIsInShouldWork() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where barcode in DEFAULT_BARCODE or UPDATED_BARCODE
        defaultSkuShouldBeFound("barcode.in=" + DEFAULT_BARCODE + "," + UPDATED_BARCODE);

        // Get all the skuList where barcode equals to UPDATED_BARCODE
        defaultSkuShouldNotBeFound("barcode.in=" + UPDATED_BARCODE);
    }

    @Test
    @Transactional
    public void getAllSkusByBarcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where barcode is not null
        defaultSkuShouldBeFound("barcode.specified=true");

        // Get all the skuList where barcode is null
        defaultSkuShouldNotBeFound("barcode.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkusByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where status equals to DEFAULT_STATUS
        defaultSkuShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the skuList where status equals to UPDATED_STATUS
        defaultSkuShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSkusByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultSkuShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the skuList where status equals to UPDATED_STATUS
        defaultSkuShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllSkusByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        // Get all the skuList where status is not null
        defaultSkuShouldBeFound("status.specified=true");

        // Get all the skuList where status is null
        defaultSkuShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllSkusBySubcategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        Subcategory subcategory = SubcategoryResourceIntTest.createEntity(em);
        em.persist(subcategory);
        em.flush();
        sku.setSubcategory(subcategory);
        skuRepository.saveAndFlush(sku);
        Long subcategoryId = subcategory.getId();

        // Get all the skuList where subcategory equals to subcategoryId
        defaultSkuShouldBeFound("subcategoryId.equals=" + subcategoryId);

        // Get all the skuList where subcategory equals to subcategoryId + 1
        defaultSkuShouldNotBeFound("subcategoryId.equals=" + (subcategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSkuShouldBeFound(String filter) throws Exception {
        restSkuMockMvc.perform(get("/api/skus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sku.getId().intValue())))
            .andExpect(jsonPath("$.[*].skuid").value(hasItem(DEFAULT_SKUID.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].barcode").value(hasItem(DEFAULT_BARCODE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSkuShouldNotBeFound(String filter) throws Exception {
        restSkuMockMvc.perform(get("/api/skus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingSku() throws Exception {
        // Get the sku
        restSkuMockMvc.perform(get("/api/skus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        int databaseSizeBeforeUpdate = skuRepository.findAll().size();

        // Update the sku
        Sku updatedSku = skuRepository.findById(sku.getId()).get();
        // Disconnect from session so that the updates on updatedSku are not directly saved in db
        em.detach(updatedSku);
        updatedSku
            .skuid(UPDATED_SKUID)
            .name(UPDATED_NAME)
            .barcode(UPDATED_BARCODE)
            .status(UPDATED_STATUS);
        SkuDTO skuDTO = skuMapper.toDto(updatedSku);

        restSkuMockMvc.perform(put("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isOk());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeUpdate);
        Sku testSku = skuList.get(skuList.size() - 1);
        assertThat(testSku.getSkuid()).isEqualTo(UPDATED_SKUID);
        assertThat(testSku.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSku.getBarcode()).isEqualTo(UPDATED_BARCODE);
        assertThat(testSku.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSku() throws Exception {
        int databaseSizeBeforeUpdate = skuRepository.findAll().size();

        // Create the Sku
        SkuDTO skuDTO = skuMapper.toDto(sku);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSkuMockMvc.perform(put("/api/skus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(skuDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sku in the database
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSku() throws Exception {
        // Initialize the database
        skuRepository.saveAndFlush(sku);

        int databaseSizeBeforeDelete = skuRepository.findAll().size();

        // Get the sku
        restSkuMockMvc.perform(delete("/api/skus/{id}", sku.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Sku> skuList = skuRepository.findAll();
        assertThat(skuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sku.class);
        Sku sku1 = new Sku();
        sku1.setId(1L);
        Sku sku2 = new Sku();
        sku2.setId(sku1.getId());
        assertThat(sku1).isEqualTo(sku2);
        sku2.setId(2L);
        assertThat(sku1).isNotEqualTo(sku2);
        sku1.setId(null);
        assertThat(sku1).isNotEqualTo(sku2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkuDTO.class);
        SkuDTO skuDTO1 = new SkuDTO();
        skuDTO1.setId(1L);
        SkuDTO skuDTO2 = new SkuDTO();
        assertThat(skuDTO1).isNotEqualTo(skuDTO2);
        skuDTO2.setId(skuDTO1.getId());
        assertThat(skuDTO1).isEqualTo(skuDTO2);
        skuDTO2.setId(2L);
        assertThat(skuDTO1).isNotEqualTo(skuDTO2);
        skuDTO1.setId(null);
        assertThat(skuDTO1).isNotEqualTo(skuDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(skuMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(skuMapper.fromId(null)).isNull();
    }
}
