package tn.esprit.spring.ServicesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Foyer;
import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.FoyerRepository;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Foyer.FoyerService;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class FoyerServiceTest {

    @InjectMocks
    private FoyerService foyerService;

    @Mock
    private FoyerRepository foyerRepository;

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.addOrUpdate(foyer);

        assertThat(result).isEqualTo(foyer);
        verify(foyerRepository).save(foyer);
    }

    @Test
    void testFindAll() {
        List<Foyer> foyers = Arrays.asList(new Foyer(), new Foyer());
        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> result = foyerService.findAll();

        assertThat(result).hasSize(2);
        verify(foyerRepository).findAll();
    }

    @Test
    void testFindById() {
        Foyer foyer = new Foyer();
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer result = foyerService.findById(1L);

        assertThat(result).isEqualTo(foyer);
        verify(foyerRepository).findById(1L);
    }

    @Test
    void testDeleteById() {
        foyerService.deleteById(1L);
        verify(foyerRepository).deleteById(1L);
    }

    @Test
    void testDelete() {
        Foyer foyer = new Foyer();
        foyerService.delete(foyer);
        verify(foyerRepository).delete(foyer);
    }

    @Test
    void testAffecterFoyerAUniversiteParNom() {
        Foyer foyer = new Foyer();
        Universite uni = new Universite();

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findByNomUniversite("ESPRIT")).thenReturn(uni);
        when(universiteRepository.save(uni)).thenReturn(uni);

        Universite result = foyerService.affecterFoyerAUniversite(1L, "ESPRIT");

        assertThat(result.getFoyer()).isEqualTo(foyer);
        verify(universiteRepository).save(uni);
    }

    @Test
    void testAffecterFoyerAUniversiteParId() {
        Foyer foyer = new Foyer();
        Universite uni = new Universite();

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findById(2L)).thenReturn(Optional.of(uni));
        when(universiteRepository.save(uni)).thenReturn(uni);

        Universite result = foyerService.affecterFoyerAUniversite(1L, 2L);

        assertThat(result.getFoyer()).isEqualTo(foyer);
    }

    @Test
    void testAjoutFoyerEtBlocs() {
        Foyer foyer = new Foyer();
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        foyer.setBlocs(Arrays.asList(bloc1, bloc2));

        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.ajoutFoyerEtBlocs(foyer);

        assertThat(result).isEqualTo(foyer);
        verify(blocRepository, times(2)).save(any(Bloc.class));
    }

    @Test
    void testAjouterFoyerEtAffecterAUniversite() {
        Foyer foyer = new Foyer();
        Bloc bloc1 = new Bloc();
        foyer.setBlocs(List.of(bloc1));
        Universite universite = new Universite();

        when(foyerRepository.save(foyer)).thenReturn(foyer);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(universite)).thenReturn(universite);

        Foyer result = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, 1L);

        assertThat(result).isEqualTo(foyer);
        verify(blocRepository).save(any(Bloc.class));
        verify(universiteRepository).save(universite);
    }

    @Test
    void testDesaffecterFoyerAUniversite() {
        Universite uni = new Universite();
        uni.setFoyer(new Foyer());

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(uni));
        when(universiteRepository.save(uni)).thenReturn(uni);

        Universite result = foyerService.desaffecterFoyerAUniversite(1L);

        assertThat(result.getFoyer()).isNull();
        verify(universiteRepository).save(uni);
    }
}
