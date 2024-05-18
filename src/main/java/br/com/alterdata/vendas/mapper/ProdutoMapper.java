package br.com.alterdata.vendas.mapper;
import br.com.alterdata.vendas.model.dto.ProdutoDTO;
import br.com.alterdata.vendas.model.entity.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "nome")
    @Mapping(target = "descricao")
    @Mapping(target = "referencia")
    @Mapping(target = "valorUnitario")
    @Mapping(source = "categoriaId", target = "categoria.id")
    Produto toProduto(ProdutoDTO produtoDTO);

}
