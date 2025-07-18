package tn.esprit.spring.RepositoriesTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Reservation;
import tn.esprit.spring.DAO.Repositories.ReservationRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testCountByAnneeUniversitaireBetween() {
        Reservation r = new Reservation();
        r.setIdReservation("R1");
        r.setAnneeUniversitaire(LocalDate.of(2023, 10, 1));
        r.setEstValide(true);
        reservationRepository.save(r);

        int count = reservationRepository.countByAnneeUniversitaireBetween(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 1, 1)
        );

        assertThat(count).isGreaterThanOrEqualTo(1);
    }

    @Test
    void testFindByEstValideAndAnneeUniversitaireBetween() {
        Reservation r1 = new Reservation();
        r1.setIdReservation("R2");
        r1.setAnneeUniversitaire(LocalDate.of(2023, 9, 1));
        r1.setEstValide(true);
        reservationRepository.save(r1);

        List<Reservation> result = reservationRepository.findByEstValideAndAnneeUniversitaireBetween(
                true,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 1, 1)
        );

        assertThat(result).isNotEmpty();
    }

    // Le test suivant nécessite que la relation avec Etudiant soit bien établie
    // avec une méthode getEtudiants() ou un champ etudiants avec un CIN valide
    // et persisté avant la réservation

    // @Test
    // void testFindByEtudiantsCinAndEstValide() {
    //     Etudiant e = new Etudiant();
    //     e.setCin(12345678L);
    //     etudiantRepository.save(e);

    //     Reservation r = new Reservation();
    //     r.setIdReservation("R3");
    //     r.setEstValide(true);
    //     r.setAnneeUniversitaire(LocalDate.now());
    //     r.setEtudiants(List.of(e)); // si @ManyToMany ou @OneToMany
    //     reservationRepository.save(r);

    //     Reservation found = reservationRepository.findByEtudiantsCinAndEstValide(12345678L, true);
    //     assertThat(found).isNotNull();
    // }

}
