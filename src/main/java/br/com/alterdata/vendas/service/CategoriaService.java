package br.com.alterdata.vendas.service;

import br.com.alterdata.vendas.exception.BusinessException;
import br.com.alterdata.vendas.mapper.CategoriaMapper;
import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import br.com.alterdata.vendas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria obterPorId(Long id) {
        return categoriaRepository.findById(id).orElseThrow(() -> new BusinessException("Categoria não encontrada."));
    }

    public Categoria obterPorTitulo(String titulo) {
        return categoriaRepository.findByTitulo(titulo).orElseThrow(() -> new BusinessException("Categoria não encontrada."));
    }

    @Transactional
    public Categoria editar(Long id, CategoriaDTO dto) throws ValidationException {
        if (categoriaRepository.existsById(id)) {
            validarCategoriaDTO(dto);
            Categoria categoria = categoriaMapper.toCategoria(dto);
            categoria.setId(id);
            return categoriaRepository.save(categoria);
        }
        throw new BusinessException("Categoria não encontrada.");
    }

    @Transactional
    public Categoria salvar(CategoriaDTO dto) throws ValidationException {
        validarCategoriaDTO(dto);
        Categoria categoria = categoriaMapper.toCategoria(dto);
        categoria.setDataCriacao(LocalDate.now());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        Categoria categoria = obterPorId(id);
        categoriaRepository.delete(categoria);
    }

    private void validarCategoriaDTO(CategoriaDTO dto) throws ValidationException {
        if (dto.getTitulo() == null || dto.getTitulo().isEmpty()) {
            throw new ValidationException("Título não pode ser nulo ou vazio");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isEmpty()) {
            throw new ValidationException("Descrição não pode ser nula ou vazia");
        }
        if (dto.getCodigo() == null || dto.getCodigo().isEmpty()) {
            throw new ValidationException("Referência não pode ser nula ou vazia");
        }
    }
}
