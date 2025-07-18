package tn.esprit.spring.ServicesTest;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.spring.DAO.Entities.Universite;
import tn.esprit.spring.DAO.Repositories.UniversiteRepository;
import tn.esprit.spring.Services.Universite.UniversiteService;

class UniversiteServiceTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteService universiteService;

    private Universite u1;
    private Universite u2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        u1 = new Universite();
        u1.setIdUniversite(1L);
        u1.setNomUniversite("Uni1");

        u2 = new Universite();
        u2.setIdUniversite(2L);
        u2.setNomUniversite("Uni2");
    }

    @Test
    void testAddOrUpdate() {
        when(universiteRepository.save(u1)).thenReturn(u1);

        Universite result = universiteService.addOrUpdate(u1);
        assertEquals(u1, result);
        verify(universiteRepository, times(1)).save(u1);
    }

    @Test
    void testFindAll() {
        when(universiteRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Universite> universites = universiteService.findAll();
        assertEquals(2, universites.size());
        assertTrue(universites.contains(u1));
        assertTrue(universites.contains(u2));
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(u1));

        Universite result = universiteService.findById(1L);
        assertEquals(u1, result);
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(universiteRepository).deleteById(1L);

        universiteService.deleteById(1L);
        verify(universiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete() {
        doNothing().when(universiteRepository).delete(u1);

        universiteService.delete(u1);
        verify(universiteRepository, times(1)).delete(u1);
    }

    @Test
    void testAjouterUniversiteEtSonFoyer() {
        when(universiteRepository.save(u1)).thenReturn(u1);

        Universite result = universiteService.ajouterUniversiteEtSonFoyer(u1);
        assertEquals(u1, result);
        verify(universiteRepository, times(1)).save(u1);
    }
}
