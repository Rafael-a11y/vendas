package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.repository.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*A anotação @Controller serve para definir a classe como controladora, é o controller que vai fazer a comunicação
* com as classe @Repository e responder às requisições HTTP. O @RequestMapping serve para definir a url raiz do sistema.
* */
@Controller
@RequestMapping("api/clientes")
public class ClienteController
{

    private Clientes clientes;

    @Autowired
    public ClienteController(Clientes clientes)
    {
        this.clientes = clientes;
    }

    /*ResponseEntity reprersenta a resposta da requisição, com ele é possível retornar o objeto junto com a mensagem de
      status 200 que representa que tudo está ok junto do json do cliente buscado pelo id, mas também se você quiser
      pode fazer com que o método retorne uma mensagem de status 404 de recurso não encontrado*/

    @GetMapping(value = {"/{codigoCliente}"})
    @ResponseBody
    public ResponseEntity getClienteById(@PathVariable(name = "codigoCliente") Integer id)
    {
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent()) return ResponseEntity.ok(cliente.get());
        return ResponseEntity.notFound().build();
    }

    /*Aqui temos o método de salvar um novo cliente na base de dados, a anotação @ResponseBody serve para definir que iremos retornar algum dado, e a
    * @RequestBody se refere ao que iremos receber do Frontend, o método salva o cliente recebido e devolve o cliente salvo junto de uma mensagem de
    * status http 200. */
    @PostMapping(value = "")
    @ResponseBody
    public ResponseEntity salvar(@RequestBody Cliente cliente)
    {
        Cliente clienteSalvo = clientes.save(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    /*Aqui temos um método de deletagem de um Cliente na base de dados, recebemos um id pela variável de caminho a partir de uma requisição http do tipo
    * delete e usamos este id para encontrar o cliente na base de dados, se este existir, usamos um método de exclusão e retornamos uma mensagem
    * http de status 204 no content, e caso ele não exista (antes de ser excluído) é retornado uma mensagem http de status 404 not found.*/

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Integer id)
    {
        Optional<Cliente> cliente = clientes.findById(id);
        if(cliente.isPresent())
        {
            clientes.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /*Aqui temos o método de atualização que recebe um Cliente e seu id, primeiro usamos o id para fazer uma busca no banco de dado, caso o
    * cliente seja encontrado no banco, usamos o id do cliente encontrado no banco para definir como id do cliente passado de parâmetro
    * no corpo da requisição e em seguida chamamos o save() para fazer a atualização.
    * Isso acontece porque o save() salva uma entidade caso esta não tenha um id (que é a nossa chave primária), mas caso a instância passada
    * para o save() já tenha um id que existe no banco de dados, o método save() irá atualizar esta instância no banco de dados.
    * Caso o cliente não seja encontrado no banco de dados com o id passado de parâmetro, então será retornada uma mensagem http de status 404 not found,
    * sim pois o objetivo deste método não salvar um novo registro e sim atualiza-lo. Caso a operação seja um sucesso, isto é o cliente seja atualizado
    * uma mensagem http de status é retornada, 204 no content, já que o propósito é tualizar e não obter dados do cliente. */
    @PutMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity atualizar(@PathVariable(name = "id") Integer id, @RequestBody Cliente cliente)
    {
        return clientes.findById(id).
                map(clienteEncontrado ->
                    {cliente.setId(clienteEncontrado.getId());
                        clientes.save(cliente);
                        return ResponseEntity.noContent().build();}).orElseGet(() -> ResponseEntity.notFound().build());
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