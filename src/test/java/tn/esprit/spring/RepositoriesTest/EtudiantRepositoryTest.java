package tn.esprit.spring.RepositoriesTest;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.DAO.Entities.Etudiant;
import tn.esprit.spring.DAO.Repositories.EtudiantRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class EtudiantRepositoryTest {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Test
    void testFindByNomEt() {
        Etudiant e = new Etudiant();
        e.setNomEt("Laabidi");
        e.setPrenomEt("Tasnim");
        e.setCin(12345678L);
        e.setDateNaissance(LocalDate.of(1995, 5, 10));
        etudiantRepository.save(e);

        List<Etudiant> found = etudiantRepository.findByNomEt("Laabidi");
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getNomEt()).isEqualTo("Laabidi");
    }

    @Test
    void testSelectJPQL() {
        List<Etudiant> list = etudiantRepository.selectJPQL("Laabidi");
        assertThat(list).isNotNull();
    }

    @Test
    void testFindByNomEtAndPrenomEt() {
        Etudiant e = new Etudiant();
        e.setNomEt("Laabidi");
        e.setPrenomEt("Tasnim");
        etudiantRepository.save(e);

        List<Etudiant> result = etudiantRepository.findByNomEtAndPrenomEt("Laabidi", "Tasnim");
        assertThat(result).isNotEmpty();
    }

    @Test
    void testUpdate() {
        Etudiant e = new Etudiant();
        e.setNomEt("OldName");
        etudiantRepository.save(e);

        etudiantRepository.update("NewSchool", e.getIdEtudiant());

        // Optionnel: recharger et vérifier
        Etudiant updated = etudiantRepository.findById(e.getIdEtudiant()).orElse(null);
        assertThat(updated).isNotNull();
        // assertThat(updated.getEcole()).isEqualTo("NewSchool"); // si tu as le champ ecole
    }
}

