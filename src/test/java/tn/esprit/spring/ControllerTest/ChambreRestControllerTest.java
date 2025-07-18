package tn.esprit.spring.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.RestControllers.ChambreRestController;
import tn.esprit.spring.Services.Chambre.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChambreRestController.class)
public class ChambreRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IChambreService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Chambre chambre = new Chambre();
        when(service.addOrUpdate(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/chambre/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(service.findAll()).thenReturn(chambres);

        mockMvc.perform(get("/chambre/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Chambre chambre = new Chambre();
        when(service.findById(1L)).thenReturn(chambre);

        mockMvc.perform(get("/chambre/findById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Chambre chambre = new Chambre();

        mockMvc.perform(delete("/chambre/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chambre)))
                .andExpect(status().isOk());

        verify(service).delete(any(Chambre.class));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/chambre/deleteById?id=1"))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void testGetChambresParNomBloc() throws Exception {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(service.getChambresParNomBloc("Bloc A")).thenReturn(chambres);

        mockMvc.perform(get("/chambre/getChambresParNomBloc?nomBloc=Bloc A"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testNbChambreParTypeEtBloc() throws Exception {
        when(service.nbChambreParTypeEtBloc(TypeChambre.SIMPLE, 1L)).thenReturn(5L);

        mockMvc.perform(get("/chambre/nbChambreParTypeEtBloc")
                        .param("type", "SIMPLE")
                        .param("idBloc", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetChambresNonReserveParNomFoyerEtTypeChambre() throws Exception {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(service.getChambresNonReserveParNomFoyerEtTypeChambre("Foyer A", TypeChambre.DOUBLE)).thenReturn(chambres);

        mockMvc.perform(get("/chambre/getChambresNonReserveParNomFoyerEtTypeChambre")
                        .param("nomFoyer", "Foyer A")
                        .param("type", "DOUBLE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

