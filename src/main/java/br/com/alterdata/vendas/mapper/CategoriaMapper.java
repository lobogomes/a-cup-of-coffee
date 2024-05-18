package br.com.alterdata.vendas.mapper;

import br.com.alterdata.vendas.model.dto.CategoriaDTO;
import br.com.alterdata.vendas.model.entity.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "titulo")
    @Mapping(target = "descricao")
    @Mapping(target = "codigo")
    Categoria toCategoria(CategoriaDTO categoriaDTO);
}
