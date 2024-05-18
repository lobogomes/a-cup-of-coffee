package br.com.alterdata.vendas.model.dto;


import java.math.BigDecimal;

public class ProdutoDTO {

    private String nome;

    private String descricao;

    private String referencia;

    private BigDecimal valorUnitario;

    private Long categoriaId;

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getReferencia() {
        return referencia;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
