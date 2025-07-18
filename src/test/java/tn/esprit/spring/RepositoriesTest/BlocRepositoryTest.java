

package tn.esprit.spring.RepositoriesTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BlocRepositoryTest {

    @Autowired
    private BlocRepository blocRepository;

    @Test
    void testFindByNomBloc() {
        // Préparer données test
        Bloc bloc = new Bloc();
        bloc.setNomBloc("BlocTest");
        bloc.setCapaciteBloc(20);
        blocRepository.save(bloc);

        // Test méthode findByNomBloc
        Bloc found = blocRepository.selectByNomBJPQL1("JPQLTest");
        assertThat(found).isNotNull(); // ✅ vérifier qu’il est trouvé
        assertThat(found.getNomBloc()).isEqualTo("JPQLTest");

    }

    @Test
    void testSelectByNomBJPQL1() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("JPQLTest");
        bloc.setCapaciteBloc(15);
        blocRepository.save(bloc);

        Bloc found = blocRepository.selectByNomBJPQL1("JPQLTest");
        assertThat(found).isNotNull();
        assertThat(found.getNomBloc()).isEqualTo("JPQLTest");
    }

    @Test
    void testFindByCapaciteBlocGreaterThan() {
        Bloc b1 = new Bloc();
        b1.setNomBloc("Bloc1");
        b1.setCapaciteBloc(5);
        blocRepository.save(b1);

        Bloc b2 = new Bloc();
        b2.setNomBloc("Bloc2");
        b2.setCapaciteBloc(20);
        blocRepository.save(b2);

        List<Bloc> result = blocRepository.findByCapaciteBlocGreaterThan(10);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNomBloc()).isEqualTo("Bloc2");
    }

    // Tu peux ajouter d'autres tests de méthodes selon le même principe
}
