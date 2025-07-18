package tn.esprit.spring.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.RestControllers.UniversiteRestController;
import tn.esprit.spring.Services.Universite.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UniversiteRestController.class)
public class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Universite universite = new Universite();
        when(service.addOrUpdate(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Universite> list = Arrays.asList(new Universite(), new Universite());
        when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/universite/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Universite universite = new Universite();
        when(service.findById(1L)).thenReturn(universite);

        mockMvc.perform(get("/universite/findById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Universite universite = new Universite();

        mockMvc.perform(delete("/universite/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk());

        verify(service).delete(any(Universite.class));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/universite/deleteById?id=1"))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void testAjouterUniversiteEtSonFoyer() throws Exception {
        Universite universite = new Universite();
        when(service.ajouterUniversiteEtSonFoyer(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/ajouterUniversiteEtSonFoyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk());
    }
}
