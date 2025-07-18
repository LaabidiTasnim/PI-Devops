package tn.esprit.spring.ServicesTest;




import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
        import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;
import tn.esprit.spring.Services.Reservation.ReservationService;

import java.time.LocalDate;
import java.util.*;

        import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAjouterReservationEtAssignerAChambreEtAEtudiant_Success() {
        // given
        Long numChambre = 101L;
        long cin = 12345678L;

        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(numChambre);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setReservations(new ArrayList<>());

        Etudiant etudiant = new Etudiant();
        etudiant.setCin(cin);

        when(chambreRepository.findByNumeroChambre(numChambre)).thenReturn(chambre);
        when(etudiantRepository.findByCin(cin)).thenReturn(etudiant);
        when(chambreRepository.countReservationsByIdChambreAndReservationsAnneeUniversitaireBetween(anyLong(), any(), any())).thenReturn(0);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArguments()[0]);
        when(chambreRepository.save(any(Chambre.class))).thenAnswer(i -> i.getArguments()[0]);

        // when
        Reservation result = reservationService.ajouterReservationEtAssignerAChambreEtAEtudiant(numChambre, cin);

        // then
        assertNotNull(result);
        assertEquals(true, result.isEstValide());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }

    @Test
    void testAnnulerReservation_Success() {
        long cinEtudiant = 12345678L;
        Reservation reservation = new Reservation();
        reservation.setIdReservation("2024/2025-A-101-12345678");
        reservation.setEstValide(true);

        Chambre chambre = new Chambre();
        chambre.setReservations(new ArrayList<>(List.of(reservation)));

        when(reservationRepository.findByEtudiantsCinAndEstValide(cinEtudiant, true)).thenReturn(reservation);
        when(chambreRepository.findByReservationsIdReservation(reservation.getIdReservation())).thenReturn(chambre);

        String result = reservationService.annulerReservation(cinEtudiant);

        assertEquals("La réservation " + reservation.getIdReservation() + " est annulée avec succés", result);
        verify(chambreRepository).save(chambre);
        verify(reservationRepository).delete(reservation);
    }
}
