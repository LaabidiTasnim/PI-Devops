package tn.esprit.spring.ServicesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Etudiant.EtudiantService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class EtudiantServiceTest {

    @InjectMocks
    private EtudiantService etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Etudiant et = new Etudiant();
        when(etudiantRepository.save(et)).thenReturn(et);

        Etudiant result = etudiantService.addOrUpdate(et);

        assertThat(result).isEqualTo(et);
        verify(etudiantRepository).save(et);
    }

    @Test
    void testFindAll() {
        List<Etudiant> list = Arrays.asList(new Etudiant(), new Etudiant());
        when(etudiantRepository.findAll()).thenReturn(list);

        List<Etudiant> result = etudiantService.findAll();

        assertThat(result).hasSize(2);
        verify(etudiantRepository).findAll();
    }

    @Test
    void testFindById() {
        Etudiant et = new Etudiant();
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(et));

        Etudiant result = etudiantService.findById(1L);

        assertThat(result).isEqualTo(et);
        verify(etudiantRepository).findById(1L);
    }

    @Test
    void testDeleteById() {
        etudiantService.deleteById(1L);
        verify(etudiantRepository).deleteById(1L);
    }

    @Test
    void testDelete() {
        Etudiant et = new Etudiant();
        etudiantService.delete(et);
        verify(etudiantRepository).delete(et);
    }

    @Test
    void testSelectJPQL() {
        List<Etudiant> list = Arrays.asList(new Etudiant());
        when(etudiantRepository.selectJPQL("Ali")).thenReturn(list);

        List<Etudiant> result = etudiantService.selectJPQL("Ali");

        assertThat(result).isEqualTo(list);
        verify(etudiantRepository).selectJPQL("Ali");
    }

    @Test
    void testAffecterReservationAEtudiant() {
        String idReservation = "res123";
        String nom = "Ali";
        String prenom = "Ben";

        Etudiant etudiant = new Etudiant();
        etudiant.setReservations(new ArrayList<>());

        Reservation reservation = new Reservation();

        when(reservationRepository.findById(idReservation)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nom, prenom)).thenReturn(etudiant);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        etudiantService.affecterReservationAEtudiant(idReservation, nom, prenom);

        assertThat(etudiant.getReservations()).contains(reservation);
        verify(etudiantRepository).save(etudiant);
    }

    @Test
    void testDesaffecterReservationAEtudiant() {
        String idReservation = "res123";
        String nom = "Ali";
        String prenom = "Ben";

        Reservation reservation = new Reservation();
        reservation.setIdReservation(idReservation);

        Etudiant etudiant = new Etudiant();
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        etudiant.setReservations(reservations); // ✅ correct

        when(reservationRepository.findById(idReservation)).thenReturn(Optional.of(reservation));
        when(etudiantRepository.getByNomEtAndPrenomEt(nom, prenom)).thenReturn(etudiant);
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        etudiantService.desaffecterReservationAEtudiant(idReservation, nom, prenom);

        assertThat(etudiant.getReservations()).doesNotContain(reservation);
        verify(etudiantRepository).save(etudiant);
    }
}
