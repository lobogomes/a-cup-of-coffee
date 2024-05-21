package br.com.alterdata.vendas.unitario.produto;

import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.mapper.ProdutoMapper;
import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.dto.FiltroDTO;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import br.com.alterdata.vendas.model.entity.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import br.com.alterdata.vendas.service.CategoriaService;
import br.com.alterdata.vendas.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.xml.bind.ValidationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @Mock
    private ProdutoMapper produtoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void deveriaListar() {

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto());

        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.listar();
        assertEquals(1, result.size());
    }

    @Test
    void deveriaObterPorId() {

        Produto produto = buildProduto();

        when(produtoRepository.findById(any())).thenReturn(Optional.of(produto));

        assertNotNull(produtoService.obterPorId(1L));
    }

    @Test
    void deveriaObterPorIdException() {

        when(produtoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.obterPorId(1L));
    }

    @Test
    void deveriaObterPorCategoria() {

        List<Produto> produtos = new ArrayList<>();
        produtos.add(new Produto());

        when(produtoRepository.findByCategoriaTitulo(any())).thenReturn(Optional.of(produtos));

        List<Produto> result = produtoService.obterPorCategoria("Eletrônicos");
        assertEquals(1, result.size());
    }

    @Test
    void deveriaObterPorCategoriaException() {

        when(produtoRepository.findByCategoriaTitulo(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.obterPorCategoria("Eletrônicos"));
    }

    @Test
    void deveriaFiltrar() {

        FiltroDTO filtro = new FiltroDTO();

        Pageable pageable = PageRequest.of(0, 10);
        when(produtoRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(new PageImpl<>(new ArrayList<>()));

        Page<Produto> result = produtoService.filtrar(filtro, pageable);
        assertNotNull(result);
    }

    @Test
    void deveriaSalvar() throws ValidationException {

        Produto produto = buildProduto();
        ProdutoDTO produtoDTO = buildProdutoDTO();

        when(produtoMapper.toProduto(any())).thenReturn(produto);
        when(categoriaService.obterPorId(any())).thenReturn(buildCategoria());
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto result = produtoService.salvar(produtoDTO);
        assertNotNull(result);
    }

    @Test
    void deveriaSalvarException() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("");
        produtoDTO.setDescricao("");
        produtoDTO.setReferencia("");
        produtoDTO.setValorUnitario(null);
        produtoDTO.setCategoriaId(null);

        assertThrows(ValidationException.class, () -> produtoService.salvar(produtoDTO));

    }

    @Test
    void deveriaEditar() throws ValidationException {

        Produto produto = buildProduto();
        ProdutoDTO produtoDTO = buildProdutoDTO();

        when(produtoRepository.existsById(any())).thenReturn(true);
        when(produtoMapper.toProduto(any())).thenReturn(produto);
        when(categoriaService.obterPorId(any())).thenReturn(buildCategoria());
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto result = produtoService.editar(1L, produtoDTO);
        assertNotNull(result);
    }

    @Test
    void deveriaEditarException() {

        ProdutoDTO produtoDTO = buildProdutoDTO();

        when(produtoRepository.existsById(any())).thenReturn(false);

        assertThrows(BusinessException.class, () -> produtoService.editar(1L, produtoDTO));
    }

    @Test
    void deveriaEditarValidationException() {

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("");
        produtoDTO.setDescricao("");
        produtoDTO.setReferencia("");
        produtoDTO.setValorUnitario(null);
        produtoDTO.setCategoriaId(null);

        when(produtoRepository.existsById(any())).thenReturn(true);

        assertThrows(ValidationException.class, () -> produtoService.editar(1L, produtoDTO));
    }

    @Test
    void deveriaDeletar() {

        Produto produto = buildProduto();

        when(produtoRepository.findById(any())).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        assertDoesNotThrow(() -> produtoService.deletar(1L));
    }

    @Test
    void deveriaDeletarException() {

        when(produtoRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> produtoService.deletar(1L));
    }

    @Test
    void deveriaIncluirCategoria() {
        Produto produto = buildProduto();
        Categoria categoria = buildCategoria();

        when(produtoRepository.existsById(any())).thenReturn(true);
        when(produtoRepository.findById(any())).thenReturn(Optional.of(produto));
        when(categoriaService.obterPorId(any())).thenReturn(categoria);
        when(produtoRepository.save(any())).thenReturn(produto);

        Produto result = produtoService.incluirCategoria(1L, 1L);
        assertNotNull(result);
    }

    @Test
    void deveriaIncluirCategoriaException() {
        when(produtoRepository.existsById(1L)).thenReturn(false);

        assertThrows(BusinessException.class, () -> produtoService.incluirCategoria(1L, 1L));
    }

    @Test
    void deveriaAlterarCategoriaPorProduto() throws ValidationException {

        Produto produto = buildProduto();
        Categoria categoria = buildCategoria();
        CategoriaDTO categoriaDTO = buildCategoriaDTO();

        when(produtoRepository.existsById(any())).thenReturn(true);
        when(produtoRepository.findById(any())).thenReturn(Optional.of(produto));
        when(categoriaService.editar(anyLong(), any(CategoriaDTO.class))).thenReturn(categoria);
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.alterarCategoriaPorProduto(1L, categoriaDTO);
        assertNotNull(result);
    }

    @Test
    void deveriaAlterarCategoriaPorProdutoException() {

        CategoriaDTO categoriaDTO = new CategoriaDTO();

        when(produtoRepository.existsById(any())).thenReturn(false);

        assertThrows(BusinessException.class, () -> produtoService.alterarCategoriaPorProduto(1L, categoriaDTO));
    }


    ProdutoDTO buildProdutoDTO() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto");
        produtoDTO.setDescricao("Descrição");
        produtoDTO.setReferencia("REF001");
        produtoDTO.setValorUnitario(new BigDecimal("10.00"));
        produtoDTO.setCategoriaId(1L);
        return produtoDTO;
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

    Produto buildProduto() {
        return new Produto(1L, "Produto", "Descrição", "REF001", new BigDecimal("10.00"), buildCategoria());
    }

}
