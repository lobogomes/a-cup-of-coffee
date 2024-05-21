package br.com.alterdata.vendas.controller;

import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.dto.FiltroDTO;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Produto;
import br.com.alterdata.vendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

@RestController
@RequestMapping("produtos")
public class ProdutosController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listar() {
        return produtoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterPorId(@PathVariable Long id) {
        Produto produto = produtoService.obterPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/categoria")
    public ResponseEntity<?> obterPorCategoria(@RequestParam String titulo) {
        List<Produto> produtos = produtoService.obterPorCategoria(titulo);
        return ResponseEntity.ok(produtos);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/filter")
    public ResponseEntity<?> filtrar(@RequestBody FiltroDTO filtro,
                                     Pageable pageable) {
        Page<Produto> produtos = produtoService.filtrar(filtro, pageable);
        return ResponseEntity.ok(produtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody ProdutoDTO dto) throws ValidationException {
        Produto produto = produtoService.editar(id, dto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}/categoria/{idCategoria}")
    public ResponseEntity<?> incluirCategoria(@PathVariable Long id, @PathVariable Long idCategoria) {
        Produto produto = produtoService.incluirCategoria(id, idCategoria);
        return ResponseEntity.ok(produto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/categoria")
    public ResponseEntity<?> alterarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO dto) throws ValidationException {
        Produto produto = produtoService.alterarCategoriaPorProduto(id, dto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody ProdutoDTO dto) throws ValidationException {
        Produto produto = produtoService.salvar(dto);
        return ResponseEntity.ok(produto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
