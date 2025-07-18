package tn.esprit.spring.RepositoriesTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UniversiteRepositoryTest {

    @Autowired
    private UniversiteRepository universiteRepository;

    @Test
    void testFindByNomUniversite() {
        Universite u = new Universite();
        u.setNomUniversite("ENISo");
        universiteRepository.save(u);

        Universite found = universiteRepository.findByNomUniversite("ENISo");
        assertThat(found).isNotNull();
        assertThat(found.getNomUniversite()).isEqualTo("ENISo");
    }

    @Test
    void testFindByFoyerCapaciteFoyerLessThan() {
        Universite u = new Universite();
        u.setNomUniversite("UnivCapTest");
        // On suppose que l’objet `Foyer` est bien lié à `Universite` avec cascade persist
        // et contient une propriété `capaciteFoyer` < 100
        // Sinon ce test devra créer et lier un Foyer avant.
        universiteRepository.save(u);

        List<Universite> result = universiteRepository.findByFoyerCapaciteFoyerLessThan(500);
        // Ce test ne fonctionnera que si la relation avec `Foyer` est bien configurée avec cascade persist
        assertThat(result).isNotEmpty();
    }

    // ⚠️ Le test suivant nécessite un modèle de données bien configuré et des entités liées :
    // Universite → Foyer → Bloc → Chambre → Reservation → Etudiant

    // @Test
    // void testFindByFoyerBlocsChambresReservationsEtudiantsNomEtLikeAndDateBetween() {
    //     Universite u = new Universite();
    //     u.setNomUniversite("CascadeTest");
    //     // Ici, tu dois construire toute la chaîne d'entités liées jusqu'à l'étudiant
    //     // avec un nom contenant "Ali" et une date de naissance entre deux dates

    //     universiteRepository.save(u);
    //
    //     List<Universite> result = universiteRepository
    //         .findByFoyerBlocsChambresReservationsEtudiantsNomEtLikeAndFoyerBlocsChambresReservationsEtudiantsDateNaissanceBetween(
    //             "%Ali%", LocalDate.of(2000, 1, 1), LocalDate.of(2005, 12, 31));
    //
    //     assertThat(result).isNotEmpty();
    // }
}
