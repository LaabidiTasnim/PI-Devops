package tn.esprit.spring.ControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.RestControllers.ReservationRestController;
import tn.esprit.spring.Services.Reservation.IReservationService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationRestController.class)
public class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddOrUpdate() throws Exception {
        Reservation reservation = new Reservation();
        when(service.addOrUpdate(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservation/addOrUpdate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        List<Reservation> list = Arrays.asList(new Reservation(), new Reservation());
        when(service.findAll()).thenReturn(list);

        mockMvc.perform(get("/reservation/findAll"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testFindById() throws Exception {
        Reservation reservation = new Reservation();
        when(service.findById("abc123")).thenReturn(reservation);

        mockMvc.perform(get("/reservation/findById?id=abc123"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(delete("/reservation/deleteById/abc123"))
                .andExpect(status().isOk());

        verify(service).deleteById("abc123");
    }

    @Test
    void testDelete() throws Exception {
        Reservation reservation = new Reservation();

        mockMvc.perform(delete("/reservation/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());

        verify(service).delete(any(Reservation.class));
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant() throws Exception {
        Reservation reservation = new Reservation();
        when(service.ajouterReservationEtAssignerAChambreEtAEtudiant(101L, 12345678L)).thenReturn(reservation);

        mockMvc.perform(post("/reservation/ajouterReservationEtAssignerAChambreEtAEtudiant")
                        .param("numChambre", "101")
                        .param("cin", "12345678"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetReservationParAnneeUniversitaire() throws Exception {
        when(service.getReservationParAnneeUniversitaire(LocalDate.parse("2023-09-01"), LocalDate.parse("2024-06-30")))
                .thenReturn(42L);

        mockMvc.perform(get("/reservation/getReservationParAnneeUniversitaire")
                        .param("debutAnnee", "2023-09-01")
                        .param("finAnnee", "2024-06-30"))
                .andExpect(status().isOk());
    }

    @Test
    void testAnnulerReservation() throws Exception {
        when(service.annulerReservation(12345678L)).thenReturn("Annulée");

        mockMvc.perform(delete("/reservation/annulerReservation")
                        .param("cinEtudiant", "12345678"))
                .andExpect(status().isOk());
    }
}
