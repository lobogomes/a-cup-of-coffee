package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.mapper.ProdutoMapper;
import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.dto.FiltroDTO;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import br.com.alterdata.vendas.model.entity.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;

import static br.com.alterdata.vendas.specification.ProdutoSpecification.buildSpecification;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoMapper produtoMapper;

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public Produto obterPorId(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new BusinessException("Produto não encontrado."));
    }

    public List<Produto> obterPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaTitulo(categoria).orElseThrow(() -> new BusinessException("Produto não encontrado."));
    }

    public Page<Produto> filtrar(FiltroDTO filtro, Pageable pageable) {
        Specification<Produto> spec = buildSpecification(filtro);
        return produtoRepository.findAll(spec, pageable);
    }

    @Transactional
    public Produto editar(Long id, ProdutoDTO dto) throws ValidationException {
        if (produtoRepository.existsById(id)) {
            validarProdutoDTO(dto);
            Produto produto = produtoMapper.toProduto(dto);
            Categoria categoria = categoriaService.obterPorId(dto.getCategoriaId());
            produto.setCategoria(categoria);
            produto.setId(id);
            return produtoRepository.save(produto);
        }
        throw new BusinessException("Produto não encontrado.");
    }

    @Transactional
    public Produto salvar(ProdutoDTO dto) throws ValidationException {
        validarProdutoDTO(dto);
        Produto produto = produtoMapper.toProduto(dto);
        Categoria categoria = categoriaService.obterPorId(dto.getCategoriaId());
        produto.setCategoria(categoria);
        return produtoRepository.save(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = obterPorId(id);
        produtoRepository.delete(produto);
    }

    @Transactional
    public Produto incluirCategoria(Long id, Long idCategoria) {
        if (produtoRepository.existsById(id)) {
            Produto produto = obterPorId(id);
            Categoria categoria = categoriaService.obterPorId(idCategoria);
            produto.setCategoria(categoria);
            return produtoRepository.save(produto);
        }
        throw new BusinessException("Produto não encontrado");
    }

    @Transactional
    public Produto alterarCategoriaPorProduto(Long id, CategoriaDTO dto) throws ValidationException {
        if (produtoRepository.existsById(id)) {
            Produto produto = obterPorId(id);
            if (Objects.nonNull(produto.getCategoria())) {
                Categoria categoria = categoriaService.editar(produto.getCategoria().getId(), dto);
                produto.setCategoria(categoria);
                return produtoRepository.save(produto);
            }
        }
        throw new BusinessException("Produto não encontrado");
    }

    private void validarProdutoDTO(ProdutoDTO dto) throws ValidationException {
        if (dto.getNome() == null || dto.getNome().isEmpty()) {
            throw new ValidationException("Nome não pode ser nulo ou vazio");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isEmpty()) {
            throw new ValidationException("Descrição não pode ser nula ou vazia");
        }
        if (dto.getReferencia() == null || dto.getReferencia().isEmpty()) {
            throw new ValidationException("Referência não pode ser nula ou vazia");
        }
        if (dto.getValorUnitario() == null) {
            throw new ValidationException("Valor unitário não pode ser nulo");
        }
        if (dto.getCategoriaId() == null) {
            throw new ValidationException("Id categoria não pode ser nulo");
        }
    }
}
