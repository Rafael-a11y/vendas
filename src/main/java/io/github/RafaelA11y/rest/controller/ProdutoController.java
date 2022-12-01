package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Produto;
import io.github.RafaelA11y.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import static org.springframework.http.HttpStatus.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController
{
    private Produtos repository;

    @Autowired
    ProdutoController(Produtos repository){this.repository = repository;}

    @GetMapping(value = {"{codigoProduto}"})
    public Produto getProdutoById(@PathVariable(name = "codigoProduto") Integer id)
    {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(NO_CONTENT, "Produto não encontrado"));
    }

    /*Adicionando a anotação @Valid para vcalidar a restrições ligadas aos campos da entidade produto.*/
    @PostMapping()
    @ResponseStatus(CREATED)
    public Produto save(@RequestBody @Valid Produto produto)
    {
        return repository.save(produto);
    }

    @DeleteMapping(value = {"{codigoProduto}"})
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable("codigoProduto") Integer id)
    {
        repository.findById(id).map(produto -> {repository.delete(produto); return produto;})
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = {"{id}"})
    public void update(@RequestBody @Valid Produto produto, @PathVariable(name = "id")  Integer id)
    {
        repository.findById(id).map((produtoEncontrado) -> {produto.setId(produtoEncontrado.getId()); repository.save(produto); return  Void.TYPE;})
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping(value = {""})
    public List<Produto> find(Produto filtro)
    {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return repository.findAll(example);
    }
}
