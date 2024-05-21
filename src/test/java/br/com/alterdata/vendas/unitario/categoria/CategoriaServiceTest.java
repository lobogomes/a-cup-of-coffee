package br.com.alterdata.vendas.unitario.categoria;

import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.mapper.CategoriaMapper;
import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import br.com.alterdata.vendas.repository.CategoriaRepository;
import br.com.alterdata.vendas.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaMapper categoriaMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deveriaListar() {

        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria());

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<Categoria> result = categoriaService.listar();
        assertEquals(1, result.size());
    }

    @Test
    void deveriaObterPorId() {

        Categoria categoria = buildCategoria();

        when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));

        assertNotNull(categoriaService.obterPorId(1L));
    }

    @Test
    void deveriaObterPorIdException() {

        when(categoriaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoriaService.obterPorId(1L));
    }

    @Test
    void deveriaObterPorTitulo() {

        Categoria categoria = buildCategoria();

        when(categoriaRepository.findByTitulo(any())).thenReturn(Optional.of(categoria));

        assertNotNull(categoriaService.obterPorTitulo("Categoria"));
    }

    @Test
    void deveriaObterPorTituloException() {

        when(categoriaRepository.findByTitulo(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoriaService.obterPorTitulo("Categoria"));
    }

    @Test
    void deveriaSalvar() throws ValidationException {

        CategoriaDTO categoriaDTO = buildCategoriaDTO();
        Categoria categoria = buildCategoria();

        when(categoriaMapper.toCategoria(any())).thenReturn(categoria);
        when(categoriaRepository.save(any())).thenReturn(categoria);
        Categoria result = categoriaService.salvar(categoriaDTO);
        assertNotNull(result);
    }

    @Test
    void deveriaSalvarException() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setTitulo("");
        categoriaDTO.setDescricao("");
        categoriaDTO.setCodigo("");

        assertThrows(ValidationException.class, () -> categoriaService.salvar(categoriaDTO));

    }

    @Test
    void deveriaEditar() throws ValidationException {

        CategoriaDTO categoriaDTO = buildCategoriaDTO();
        Categoria categoria = buildCategoria();

        when(categoriaRepository.existsById(any())).thenReturn(true);
        when(categoriaMapper.toCategoria(any())).thenReturn(categoria);
        when(categoriaRepository.save(any())).thenReturn(categoria);

        Categoria result = categoriaService.editar(1L, categoriaDTO);
        assertNotNull(result);
    }

    @Test
    void deveriaEditarException() {

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        when(categoriaRepository.existsById(any())).thenReturn(false);

        assertThrows(BusinessException.class, () -> categoriaService.editar(1L, categoriaDTO));
    }

    @Test
    void deveriaEditarValidationException() {

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setTitulo("");
        categoriaDTO.setDescricao("");
        categoriaDTO.setCodigo("");

        when(categoriaRepository.existsById(any())).thenReturn(true);

        assertThrows(ValidationException.class, () -> categoriaService.editar(1L, categoriaDTO));
    }

    @Test
    void deveriaDeletar() {

        Categoria categoria = buildCategoria();

        when(categoriaRepository.findById(any())).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(categoria);

        assertDoesNotThrow(() -> categoriaService.deletar(1L));
    }

    @Test
    void deveriaDeletarException() {

        when(categoriaRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> categoriaService.deletar(1L));
    }


    CategoriaDTO buildCategoriaDTO() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setTitulo("Categoria");
        categoriaDTO.setDescricao("Descrição");
        categoriaDTO.setCodigo("CD1");

        return categoriaDTO;
    }

    Categoria buildCategoria() {
        return new Categoria(1L, "Categoria", "Descrição", "CD1", LocalDate.now());
    }

}
