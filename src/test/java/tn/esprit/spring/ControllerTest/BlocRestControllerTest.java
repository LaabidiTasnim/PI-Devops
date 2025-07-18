package tn.esprit.spring.ControllerTest;





import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.RestControllers.BlocRestController;
import tn.esprit.spring.Services.Bloc.IBlocService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlocRestController.class)
class BlocRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBlocService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Bloc bloc = new Bloc();
        when(service.addOrUpdate(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(post("/bloc/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Bloc> blocs = Arrays.asList(new Bloc(), new Bloc());
        when(service.findAll()).thenReturn(blocs);

        mockMvc.perform(get("/bloc/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Bloc bloc = new Bloc();
        when(service.findById(1L)).thenReturn(bloc);

        mockMvc.perform(get("/bloc/findById?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() throws Exception {
        Bloc bloc = new Bloc();

        mockMvc.perform(delete("/bloc/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk());

        verify(service).delete(any(Bloc.class));
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/bloc/deleteById?id=1"))
                .andExpect(status().isOk());

        verify(service).deleteById(1L);
    }

    @Test
    void testAffecterChambresABloc() throws Exception {
        List<Long> chambres = Arrays.asList(1L, 2L);
        Bloc bloc = new Bloc();
        when(service.affecterChambresABloc(anyList(), eq("Bloc A"))).thenReturn(bloc);

        mockMvc.perform(put("/bloc/affecterChambresABloc?nomBloc=Bloc A")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chambres)))
                .andExpect(status().isOk());
    }

    @Test
    void testAffecterBlocAFoyer() throws Exception {
        Bloc bloc = new Bloc();
        when(service.affecterBlocAFoyer("Bloc G", "Foyer des jasmins")).thenReturn(bloc);

        mockMvc.perform(put("/bloc/affecterBlocAFoyer?nomBloc=Bloc G&nomFoyer=Foyer des jasmins"))
                .andExpect(status().isOk());
    }

    @Test
    void testAffecterBlocAFoyer2() throws Exception {
        Bloc bloc = new Bloc();
        when(service.affecterBlocAFoyer("Bloc G", "Foyer des jasmins")).thenReturn(bloc);

        mockMvc.perform(put("/bloc/affecterBlocAFoyer2/Foyer des jasmins/Bloc G"))
                .andExpect(status().isOk());
    }

    @Test
    void testAjouterBlocEtSesChambres() throws Exception {
        Bloc bloc = new Bloc();
        when(service.ajouterBlocEtSesChambres(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(post("/bloc/ajouterBlocEtSesChambres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk());
    }

    @Test
    void testAjouterBlocEtAffecterAFoyer() throws Exception {
        Bloc bloc = new Bloc();
        when(service.ajouterBlocEtAffecterAFoyer(any(Bloc.class), eq("Foyer X"))).thenReturn(bloc);

        mockMvc.perform(post("/bloc/ajouterBlocEtAffecterAFoyer/Foyer X")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk());
    }
}
