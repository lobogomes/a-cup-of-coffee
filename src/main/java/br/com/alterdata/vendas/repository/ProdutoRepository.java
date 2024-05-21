package br.com.alterdata.vendas.repository;

import br.com.alterdata.vendas.model.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    Optional<List<Produto>> findByCategoriaTitulo(String categoriaTitulo);

    Page<Produto> findAll(Specification<Produto> spec, Pageable pageable);
}
