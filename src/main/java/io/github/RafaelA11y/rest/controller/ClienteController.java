package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.Cliente;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/*A anotação @Controller serve para definir a classe como controladora, é o controller que vai fazer a comunicação
* com as classe @Repository e responder às requisições HTTP. O @RequestMapping serve para definir a url raiz do sistema.
* O abre e fecha chaves dentro  do parênteses serve para definir uma variável de caminho e o @PathVariable serve para
* mapear a variável de caminho com o parâmetro String nomeCliente do método heloClientes(). A propriedade value aceita
* um array com várias url's que você pode definir.
* A propriedade consumes serve para definir qual o formato de arquivo que este método vai receber da requisição, neste
* caso, será um objeto Cliente cliente tanto no formato json quanto xml, a anotação @RequestBody serve para definir
* que o objeto Cliente cliente recebido de parâmetro será obtido a partir da requisição, ou seja, o objeto Cliente
* cliente vai vir tanto em fommato json quanto xml. A propriedade produces é análoga a consumes, define em que formato
* de arquivo o objeto será retornado, no casso o objeto Cliente que é retornado com o nome também passado de prâmetro.
* Será retornado um objeto Cliente em formato json e xml*/
@Controller
@RequestMapping("api/clientes")
public class ClienteController
{
//    @RequestMapping(
//            value = {"/hello/{nome}", "/ola/{nome}"},
//            method = RequestMethod.POST,
//            consumes = {"application/json", "application/xml"},
//            produces = {"application/json", "application/xml"}) @ResponseBody
//    public Cliente heloClientes(@PathVariable("nome") String nomeCliente,@RequestBody Cliente cliente)
//    {
//        return new Cliente(nomeCliente);
//    }


}
