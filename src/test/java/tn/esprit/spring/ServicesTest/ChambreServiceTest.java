package tn.esprit.spring.ServicesTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.spring.DAO.Entities.Bloc;
import tn.esprit.spring.DAO.Entities.Chambre;
import tn.esprit.spring.DAO.Entities.TypeChambre;
import tn.esprit.spring.DAO.Repositories.BlocRepository;
import tn.esprit.spring.DAO.Repositories.ChambreRepository;
import tn.esprit.spring.Services.Chambre.ChambreService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ChambreServiceTest {

    @InjectMocks
    private ChambreService chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @Mock
    private BlocRepository blocRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddOrUpdate() {
        Chambre chambre = new Chambre();
        when(chambreRepository.save(chambre)).thenReturn(chambre);

        Chambre result = chambreService.addOrUpdate(chambre);

        assertThat(result).isEqualTo(chambre);
        verify(chambreRepository).save(chambre);
    }

    @Test
    void testFindAll() {
        List<Chambre> chambres = Arrays.asList(new Chambre(), new Chambre());
        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> result = chambreService.findAll();

        assertThat(result).hasSize(2);
        verify(chambreRepository).findAll();
    }

    @Test
    void testFindById() {
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre result = chambreService.findById(1L);

        assertThat(result).isEqualTo(chambre);
        verify(chambreRepository).findById(1L);
    }

    @Test
    void testDeleteById() {
        chambreService.deleteById(1L);
        verify(chambreRepository).deleteById(1L);
    }

    @Test
    void testDelete() {
        Chambre chambre = new Chambre();
        chambreService.delete(chambre);
        verify(chambreRepository).delete(chambre);
    }

    @Test
    void testGetChambresParNomBloc() {
        String nomBloc = "Bloc A";
        List<Chambre> chambres = Arrays.asList(new Chambre());
        when(chambreRepository.findByBlocNomBloc(nomBloc)).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomBloc(nomBloc);

        assertThat(result).isEqualTo(chambres);
        verify(chambreRepository).findByBlocNomBloc(nomBloc);
    }

    @Test
    void testNbChambreParTypeEtBloc() {
        // given
        TypeChambre type = TypeChambre.SIMPLE;
        long idBloc = 1L;

        Bloc bloc = new Bloc();
        bloc.setIdBloc(idBloc);

        Chambre c1 = new Chambre();
        c1.setTypeC(TypeChambre.SIMPLE);
        c1.setBloc(bloc);

        Chambre c2 = new Chambre();
        c2.setTypeC(TypeChambre.DOUBLE);
        c2.setBloc(bloc);

        List<Chambre> allChambres = Arrays.asList(c1, c2);
        when(chambreRepository.findAll()).thenReturn(allChambres);

        // when
        long count = chambreService.nbChambreParTypeEtBloc(type, idBloc);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testGetChambresParNomBlocJava() {
        Bloc bloc = new Bloc();
        bloc.setChambres(Arrays.asList(new Chambre(), new Chambre()));
        when(blocRepository.findByNomBloc("BlocX")).thenReturn(bloc);

        List<Chambre> result = chambreService.getChambresParNomBlocJava("BlocX");

        assertThat(result).hasSize(2);
        verify(blocRepository).findByNomBloc("BlocX");
    }

    @Test
    void testGetChambresParNomBlocJPQL() {
        List<Chambre> chambres = Arrays.asList(new Chambre());
        when(chambreRepository.getChambresParNomBlocJPQL("BlocJPQL")).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomBlocJPQL("BlocJPQL");

        assertThat(result).isEqualTo(chambres);
        verify(chambreRepository).getChambresParNomBlocJPQL("BlocJPQL");
    }

    @Test
    void testGetChambresParNomBlocSQL() {
        List<Chambre> chambres = Arrays.asList(new Chambre());
        when(chambreRepository.getChambresParNomBlocSQL("BlocSQL")).thenReturn(chambres);

        List<Chambre> result = chambreService.getChambresParNomBlocSQL("BlocSQL");

        assertThat(result).isEqualTo(chambres);
        verify(chambreRepository).getChambresParNomBlocSQL("BlocSQL");
    }

}
