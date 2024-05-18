package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.mapper.ProdutoMapper;
import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import br.com.alterdata.vendas.model.entity.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Objects;

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

    @Transactional
    public Produto editar(Long id, ProdutoDTO dto) throws ValidationException {
        if (produtoRepository.existsById(id)) {
            validarProdutoDTO(dto);
            try {
                Produto produto = produtoMapper.toProduto(dto);
                produto.setId(id);
                return produtoRepository.save(produto);
            } catch (DataIntegrityViolationException e) {
                throw new BusinessException("Erro de integridade de dados ao salvar o produto.");
            } catch (Exception e) {
                throw new RuntimeException("Erro inesperado ao salvar o produto.");
            }
        }
        throw new BusinessException("Produto não encontrado.");
    }

    @Transactional
    public Produto salvar(ProdutoDTO dto) throws ValidationException {
        validarProdutoDTO(dto);
            Produto produto = produtoMapper.toProduto(dto);
            return produtoRepository.save(produto);
    }

    @Transactional
    public void deletar(Long id) {
        try {
            Produto produto = obterPorId(id);
            produtoRepository.delete(produto);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao deletar produto.");
        }
    }

    @Transactional
    public Produto incluirCategoria(Long id, Long idCategoria) {
        if (produtoRepository.existsById(id)){
            try {
                Produto produto = obterPorId(id);
                Categoria categoria = categoriaService.obterPorId(idCategoria);
                produto.setCategoria(categoria);
                return produtoRepository.save(produto);
            }catch (Exception e){
                throw new RuntimeException("Erro inesperado ao alterar categoria do produto");
            }
        }
        throw new BusinessException("Produto não encontrado");
    }

    @Transactional
    public Produto alterarCategoriaPorProduto(Long id, CategoriaDTO dto){
        if (produtoRepository.existsById(id)){
            try {
                Produto produto = obterPorId(id);
                if (Objects.nonNull(produto.getCategoria())){
                    Categoria categoria = categoriaService.editar(produto.getCategoria().getId(), dto);
                    produto.setCategoria(categoria);
                    return produtoRepository.save(produto);
                }
            }catch (Exception e){
                throw new RuntimeException("Erro inesperado ao alterar categoria do produto");
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
    }
}
