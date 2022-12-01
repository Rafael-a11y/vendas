package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.Valid;
import java.util.List;

/*A anotação @RestController serve para definir a classe como controladora além de ser uma especificação de @Controller, com essa anotação não é preciso
  usar a anotação @ResponseBody nos métodos que retornam alguma coisa, é o controller que vai fazer a comunicação com as classe @Repository e responder
  às requisições HTTP. O @RequestMapping serve para definir a url raiz do sistema.
* */
@RestController
@RequestMapping("api/clientes")
public class ClienteController
{

    private Clientes clientes;

    @Autowired
    public ClienteController(Clientes clientes)
    {
        this.clientes = clientes;
    }

    /*ResponseEntity representa a resposta da requisição, com ele é possível retornar o objeto junto com a mensagem de
      status 200 que representa que tudo está ok junto do json do cliente buscado pelo id, mas também se você quiser
      pode fazer com que o método retorne uma mensagem de status 404 de recurso não encontrado*/

    @GetMapping(value = {"{codigoCliente}"})
    public Cliente getClienteById(@PathVariable(name = "codigoCliente") Integer id)
    {
        return clientes.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado HEHEEHEHEHEHE"));
    }

    /*Aqui temos o método de salvar um novo cliente na base de dados, a anotação @ResponseBody serve para definir que iremos retornar algum dado, e a
    * @RequestBody se refere ao que iremos receber do Frontend, o método salva o cliente recebido e devolve o cliente salvo junto de uma mensagem de
    * status http 200. Também é preciso adicionar a anotação @Valid porque a entidade Cliente usa javax.validation.constraints em um de seus campos.
    * Caso @Valid não seja declarado, uma execução em tempo de execução acontecerá e uma mensagem 500 será gerada. */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid Cliente cliente)
    {
        return clientes.save(cliente);

    }

    /*Aqui temos um método de deletagem de um Cliente na base de dados, recebemos um id pela variável de caminho a partir de uma requisição http do tipo
    * delete e usamos este id para encontrar o cliente na base de dados, se este existir, usamos um método de exclusão e retornamos uma mensagem
    * http de status 204 no content, e caso ele não exista (antes de ser excluído) é retornado uma mensagem http de status 404 not found.*/

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Integer id)
    {
        clientes.findById(id)
                .map(cliente -> {clientes.delete(cliente); return cliente; })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cliente não encontrado."));
    }



    /*Aqui temos o método de atualização que recebe um Cliente e seu id, primeiro usamos o id para fazer uma busca no banco de dados, caso o
    * cliente seja encontrado no banco, usamos o id do cliente encontrado no banco para definir como id do cliente passado de parâmetro
    * no corpo da requisição e em seguida chamamos o save() para fazer a atualização.
    * Isso acontece porque o save() salva uma entidade caso esta não tenha um id (que é a nossa chave primária), mas caso a instância passada
    * para o save() já tenha um id que existe no banco de dados, o método save() irá atualizar esta instância no banco de dados.
    * Caso o cliente não seja encontrado no banco de dados com o id passado de parâmetro, então será retornada uma mensagem http de status 404 not found,
    * sim pois o objetivo deste método não é salvar um novo registro e sim atualiza-lo. Caso a operação seja um sucesso, isto é o cliente seja atualizado
    * uma mensagem http de status é retornada, 204 no content, já que o propósito é tualizar e não obter dados do cliente. */
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable(name = "id") Integer id, @RequestBody @Valid Cliente cliente)
    {
        clientes.findById(id).map(
                                    clienteEncontrado ->
                                        {
                                            cliente.setId(clienteEncontrado.getId());
                                            clientes.save(cliente);
                                            return clienteEncontrado;
                                        }
                                ).orElseThrow(
                                        () ->
                                                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado HAHAHAHAHHAHAHAHH"));
    }

    /*ExampleMatcher mather serve para aplicar os devidos filtros de busca, matcher serve de parâmetro para Example of(T entity, ExampleMatcher param)
      junto do objeto passado de parâmetro no nosso método find(). Logo usamos o método findAll(Example param) para aplicar o filtro em todos os clientes na
      base de dados, o nosso método find() retorna a lista da busca com um http status 200 ok.*/
    @GetMapping()
    public List<Cliente> find(Cliente filtro)
    {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clientes.findAll(example);
    }
}

/*O abre e fecha chaves dentro  do parênteses serve para definir uma variável de caminho e o @PathVariable serve para
 * mapear a variável de caminho com o parâmetro String nomeCliente do método heloClientes(). A propriedade value aceita
 * um array com várias url's que você pode definir.
 * A propriedade consumes serve para definir qual o formato de arquivo que este método vai receber da requisição, neste
 * caso, será um objeto Cliente cliente tanto no formato json quanto xml, a anotação @RequestBody serve para definir
 * que o objeto Cliente cliente recebido de parâmetro será obtido a partir da requisição, ou seja, o objeto Cliente
 * cliente vai vir tanto em fommato json quanto xml. A propriedade produces é análoga a consumes, define em que formato
 * de arquivo o objeto será retornado, no casso o objeto Cliente que é retornado com o nome também passado de prâmetro.
 * Será retornado um objeto Cliente em formato json e xml*/

//    @RequestMapping(
//            value = {"/hello/{nome}", "/ola/{nome}"},
//            method = RequestMethod.POST,
//            consumes = {"application/json", "application/xml"},
//            produces = {"application/json", "application/xml"}) @ResponseBody
//    public Cliente heloClientes(@PathVariable("nome") String nomeCliente,@RequestBody Cliente cliente)
//    {
//        return new Cliente(nomeCliente);
//    }