package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Produto;
import io.github.RafaelA11y.domain.repository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController
{
    private Produtos produtos;

    @Autowired
    ProdutoController(Produtos produtos){this.produtos = produtos;}

    @GetMapping(value = {"{codigoProduto}"})
    public Produto getProdutoById(@PathVariable(name = "codigoProduto") Integer id)
    {
        return produtos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Produto não encontrado"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Produto salvar(@RequestBody Produto produto)
    {
        return produtos.save(produto);
    }

    @DeleteMapping(value = {"{codigoProduto}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("codigoProduto") Integer id)
    {
        produtos.findById(id).map(produto -> {produtos.delete(produto); return produto;})
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }


}
