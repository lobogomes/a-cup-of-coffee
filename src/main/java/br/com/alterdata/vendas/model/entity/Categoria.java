package br.com.alterdata.vendas.model.entity;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "categorias")
@Data
public class Categoria {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "titulo")
    private String titulo;

    @NotNull
    @Column(name = "descricao")
    private String descricao;

    @NotNull
    @Column(name = "codigo")
    private String codigo;

    @NotNull
    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    public Categoria(Long id, String titulo, String descricao, String codigo, LocalDate dataCriacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.codigo = codigo;
        this.dataCriacao = dataCriacao;
    }

    public Categoria() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
