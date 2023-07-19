package io.github.RafaelA11y.domain.repository;

import io.github.RafaelA11y.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface Produtos extends JpaRepository<Produto, Integer>
{
    public List<Produto> findByDescricaoLike(String descricao);

    public boolean existsByDescricao(String descricao);

    @Query(value = "select p from Produto p where p.descricao like :descricao")
    List<Produto> encontrarPorDescricao(@Param("descricao") String descricao);

    @Query(value = "select * from produto p where p.descricao like '%:descricao%'", nativeQuery = true)
    List<Produto> encontrarPorDescricaoComSqlNativo(@Param("descricao") String descricao);

    @Transactional
    void deleteByDescricao(String descricao);

    @Transactional
    @Modifying
    @Query("delete from Produto p where p.descricao = :descricao")
    void deleteByDescricaoComQuery(@Param("descricao") String descricao);

    @Query(value = "select p from Produto p where p.id = :id")
    Produto findProdutoById(Integer id);

    Optional<Produto> findById(Integer id);



}
