package tn.esprit.spring.RepositoriesTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class FoyerRepositoryTest {

    @Autowired
    private FoyerRepository foyerRepository;

    @Test
    void testFindByNomFoyer() {
        Foyer f = new Foyer();
        f.setNomFoyer("FoyerTest");
        f.setCapaciteFoyer(100);
        foyerRepository.save(f);

        Foyer found = foyerRepository.findByNomFoyer("FoyerTest");
        assertThat(found).isNotNull();
        assertThat(found.getNomFoyer()).isEqualTo("FoyerTest");
    }

    @Test
    void testFindByCapaciteFoyerGreaterThan() {
        Foyer f = new Foyer();
        f.setNomFoyer("Foyer Grand");
        f.setCapaciteFoyer(200);
        foyerRepository.save(f);

        List<Foyer> result = foyerRepository.findByCapaciteFoyerGreaterThan(150);
        assertThat(result).isNotEmpty();
    }

    @Test
    void testFindByCapaciteFoyerBetween() {
        Foyer f = new Foyer();
        f.setNomFoyer("Foyer Moyen");
        f.setCapaciteFoyer(80);
        foyerRepository.save(f);

        List<Foyer> list = foyerRepository.findByCapaciteFoyerBetween(50, 100);
        assertThat(list).isNotEmpty();
    }

    @Test
    void testFindByUniversiteNomUniversite() {
        // Ce test suppose que tu as une relation correcte entre Foyer et Universite
        // et que l’entité Universite a bien été persistée et liée au foyer.

        // Foyer foyer = new Foyer();
        // foyer.setNomFoyer("TestFoyer");
        // Universite u = new Universite();
        // u.setNomUniversite("ENIS");
        // foyer.setUniversite(u);
        // foyerRepository.save(foyer);

        // Foyer found = foyerRepository.findByUniversiteNomUniversite("ENIS");
        // assertThat(found).isNotNull();

        // ⚠️ Ce test fonctionne uniquement si la relation ManyToOne ou OneToOne est bien définie avec cascade persist
    }
}
