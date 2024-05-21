package br.com.alterdata.vendas.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
public class Produto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nome")
    private String nome;

    @NotNull
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "referencia")
    private String referencia;

    @NotNull
    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    public Produto(Long id, String nome, String descricao, String referencia, BigDecimal valorUnitario, Categoria categoria) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.referencia = referencia;
        this.valorUnitario = valorUnitario;
        this.categoria = categoria;
    }

    public Produto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
