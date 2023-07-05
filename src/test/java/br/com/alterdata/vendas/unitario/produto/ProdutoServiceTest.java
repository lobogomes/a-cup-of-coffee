package br.com.alterdata.vendas.unitario.produto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import br.com.alterdata.vendas.model.Produto;
import br.com.alterdata.vendas.repository.ProdutoRepository;
import br.com.alterdata.vendas.service.ProdutoService;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock ProdutoRepository produtoRepository;
    @InjectMocks private ProdutoService service;

    @Test
    @DisplayName("deveria retornar a listagem de produtos")
    public void deveriaRetornarListagemProdutos() {
        service.listar();
        verify(produtoRepository, times(1)).findAll();
    }
}
