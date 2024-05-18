package br.com.alterdata.vendas.controller;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Produto;
import br.com.alterdata.vendas.service.ProdutoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody ProdutoDTO dto) throws ValidationException {
        Produto produto = produtoService.editar(id, dto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping()
    public ResponseEntity<?> salvar(@RequestBody ProdutoDTO dto) throws ValidationException {
        Produto produto = produtoService.salvar(dto);
        return ResponseEntity.ok(produto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
