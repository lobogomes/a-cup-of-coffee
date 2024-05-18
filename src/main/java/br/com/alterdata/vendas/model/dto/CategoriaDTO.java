package br.com.alterdata.vendas.model.dto;


import br.com.alterdata.vendas.model.entity.Produto;

import java.time.LocalDate;
import java.util.List;

public class CategoriaDTO {

    private String titulo;

    private String descricao;

    private String codigo;

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
