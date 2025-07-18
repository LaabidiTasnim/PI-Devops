package tn.esprit.spring.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.RestControllers.EtudiantRestController;
import tn.esprit.spring.Services.Etudiant.IEtudiantService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EtudiantRestController.class)
public class EtudiantRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEtudiantService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Etudiant etudiant = new Etudiant();
        when(service.addOrUpdate(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Etudiant> list = Arrays.asList(new Etudiant(), new Etudiant());
        when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/etudiant/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Etudiant etudiant = new Etudiant();
        when(service.findById(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/findById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Etudiant etudiant = new Etudiant();

        mockMvc.perform(delete("/etudiant/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(etudiant)))
                .andExpect(status().isOk());

        verify(service).delete(any(Etudiant.class));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/etudiant/deleteById?id=1"))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void testSelectJPQL() throws Exception {
        List<Etudiant> list = Arrays.asList(new Etudiant(), new Etudiant());
        when(service.selectJPQL("Ben Ali")).thenReturn(list);

        mockMvc.perform(get("/etudiant/selectJPQL?nom=Ben Ali"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
