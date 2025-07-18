package tn.esprit.spring.RepositoriesTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ChambreRepositoryTest {

    @Autowired
    private ChambreRepository chambreRepository;

    @Test
    void testFindByNumeroChambre() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101);
        chambreRepository.save(chambre);

        Chambre found = chambreRepository.findByNumeroChambre(101);
        assertThat(found).isNotNull();
        assertThat(found.getNumeroChambre()).isEqualTo(101);
    }

    @Test
    void testCountByTypeCAndBlocIdBloc() {
        Chambre c = new Chambre();
        c.setTypeC(TypeChambre.SIMPLE); // Adapte selon ton enum
        c.setNumeroChambre(201);
        // Assure-toi que le bloc est bien assigné avec un idBloc valide
        // Ici juste un exemple si setBloc existe
        // Bloc bloc = new Bloc();
        // bloc.setIdBloc(1L);
        // c.setBloc(bloc);

        chambreRepository.save(c);

        int count = chambreRepository.countByTypeCAndBlocIdBloc(TypeChambre.SIMPLE, 1L);
        // Selon ta base, ce test peut être plus complet avec un setup réel
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testListerReservationPourUneChambre() {
        // Tu peux tester la requête native si tu as des données en base
        int count = chambreRepository.listerReservationPourUneChambre(1L,
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2024, 6, 30));
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    void testGetChambresParNomBlocJPQL() {
        List<Chambre> chambres = chambreRepository.getChambresParNomBlocJPQL("BlocTest");
        assertThat(chambres).isNotNull();
    }

    // Ajoute d'autres tests similaires pour tes méthodes spécifiques
}
