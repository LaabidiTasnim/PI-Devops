package tn.esprit.spring.ServicesTest;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.DAO.Entities.*;
import tn.esprit.spring.DAO.Repositories.*;
import tn.esprit.spring.Services.Bloc.BlocService;

@SpringBootTest
public class BlocServiceTest {

    @InjectMocks
    private BlocService blocService;

    @Mock
    private BlocRepository blocRepository;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private FoyerRepository foyerRepository;

    private Bloc bloc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(10);
        bloc.setChambres(new ArrayList<>());
    }

    @Test
    void testAddOrUpdate() {
        Chambre chambre = new Chambre();
        chambre.setNumeroChambre(101L);
        bloc.getChambres().add(chambre);

        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Bloc saved = blocService.addOrUpdate(bloc);

        assertThat(saved).isNotNull();
        verify(chambreRepository, times(1)).save(any(Chambre.class));
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testFindById() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        Bloc result = blocService.findById(1L);
        assertThat(result).isEqualTo(bloc);
    }

    @Test
    void testFindAll() {
        List<Bloc> blocs = List.of(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);
        List<Bloc> result = blocService.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void testDeleteById() {
        bloc.setChambres(List.of(new Chambre(), new Chambre()));
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));
        blocService.deleteById(1L);
        verify(chambreRepository).deleteAll(anyList());
        verify(blocRepository).delete(bloc);
    }

    @Test
    void testAffecterChambresABloc() {
        Bloc bloc = new Bloc();
        bloc.setNomBloc("Bloc A");

        Chambre chambre1 = new Chambre();
        chambre1.setNumeroChambre(101L);
        Chambre chambre2 = new Chambre();
        chambre2.setNumeroChambre(102L);

        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(chambreRepository.findByNumeroChambre(101L)).thenReturn(chambre1);
        when(chambreRepository.findByNumeroChambre(102L)).thenReturn(chambre2);

        Bloc result = blocService.affecterChambresABloc(List.of(101L, 102L), "Bloc A");

        assertThat(result).isEqualTo(bloc);
        verify(chambreRepository, times(2)).save(any(Chambre.class));
    }

    @Test
    void testAffecterBlocAFoyer() {
        Foyer foyer = new Foyer();
        when(blocRepository.findByNomBloc("Bloc A")).thenReturn(bloc);
        when(foyerRepository.findByNomFoyer("Foyer 1")).thenReturn(foyer);
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc result = blocService.affecterBlocAFoyer("Bloc A", "Foyer 1");

        assertThat(result.getFoyer()).isEqualTo(foyer);
    }
}
