package tn.esprit.spring.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.RestControllers.FoyerRestController;
import tn.esprit.spring.Services.Foyer.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FoyerRestController.class)
public class FoyerRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFoyerService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Foyer foyer = new Foyer();
        when(service.addOrUpdate(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Foyer> foyers = Arrays.asList(new Foyer(), new Foyer());
        when(service.findAll()).thenReturn(foyers);

        mockMvc.perform(get("/foyer/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Foyer foyer = new Foyer();
        when(service.findById(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/findById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Foyer foyer = new Foyer();

        mockMvc.perform(delete("/foyer/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk());

        verify(service).delete(any(Foyer.class));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/foyer/deleteById?id=1"))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void testAffecterFoyerAUniversite() throws Exception {
        Universite universite = new Universite();
        when(service.affecterFoyerAUniversite(1L, "ESPRIT")).thenReturn(universite);

        mockMvc.perform(put("/foyer/affecterFoyerAUniversite?idFoyer=1&nomUniversite=ESPRIT"))
                .andExpect(status().isOk());
    }

    @Test
    void testDesaffecterFoyerAUniversite() throws Exception {
        Universite universite = new Universite();
        when(service.desaffecterFoyerAUniversite(2L)).thenReturn(universite);

        mockMvc.perform(put("/foyer/desaffecterFoyerAUniversite?idUniversite=2"))
                .andExpect(status().isOk());
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() throws Exception {
        Foyer foyer = new Foyer();
        when(service.ajouterFoyerEtAffecterAUniversite(any(Foyer.class), eq(3L))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/ajouterFoyerEtAffecterAUniversite?idUniversite=3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk());
    }

    @Test
    void testAffecterFoyerAUniversitePathVariable() throws Exception {
        Universite universite = new Universite();
        when(service.affecterFoyerAUniversite(4L, 5L)).thenReturn(universite);

        mockMvc.perform(put("/foyer/affecterFoyerAUniversite/4/5"))
                .andExpect(status().isOk());
    }
}
