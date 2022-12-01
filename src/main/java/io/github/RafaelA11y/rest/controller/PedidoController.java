package io.github.RafaelA11y.rest.controller;

import io.github.RafaelA11y.domain.entity.ItemPedido;
import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.domain.enums.StatusPedido;
import io.github.RafaelA11y.rest.dto.*;
import io.github.RafaelA11y.service.PedidoService;
import  static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"api/pedidos"})
public class PedidoController
{
    // PedidoService é uma interface, quem de fato vai ser responsável por gerenciar as regras de negócio é sua classe implementadora.
    private PedidoService service;

    public PedidoController(PedidoService service)
    {
        this.service = service;
    }

    /*Faz a inserção de um novo pedido na base de dados a partir de um json que representa o PedidoDTO que será usado para a criação do novo pedido
    * na base de dados, o método retorna uma http status created, e o id do pedido gerado na base de dados. A anotação @Valid serve para que as restrições
    * aplicadas aos camspo de PedidoDTO sejam validadas.*/
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto)
    {
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }
    /*Retorna o InformacoesPedidoDTO para o front e ser exibido para o usuário, o InformacoesPedidoDto é usado no lugar da entidade Pedido pois esta
    * possui muitas informações que não precisam ser exibidas para o usuário (como o id do cliente que seria exibido se a entidade Pedido fosse
    * retornada no método ao invés de InformacoesPedidoDTO). Como o método PedidoService obterPedidoCompleto(Integer id): Optional<Pedido> pode retornar
    * valor nulo, usa-se suplier orElseThrow para lançar a exceção. Caso o método retorne um Optional<Pedido> chama-se o Function map() para converter
    * o Pedido dentro do Optional em InfomaçõesPedidoDto através do padrão Builder e retornar este objeto convertido.*/
    @GetMapping(value = {"{id}"})
    public InformacoesPedidoDTO getById(@PathVariable(value = "id") Integer id)
    {
        return service.obterPedidoCompleto(id).map(p -> this.converter(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
    }

    /*O método http put precisa de todos os campos informados para não preencher com nulo o campo do registro no banco de dados que não foi informado.
    * Para isso não acontecer, usa-se o @Patch que também é um verbo http de atualuzação, mas serve para o caso em que queremos fazer apenas uma
    * atualização particionada. O método estático da enum valueOf(String name) recebe uma String e se essa String for exatamente igual a um dos campos
    * da enum, este srá convertido no campo equivalente.*/
    @PatchMapping(value = {"{id}"})
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable(name = "id") Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto)
    {
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatusPedido(id, StatusPedido.valueOf(novoStatus));
    }

    /*Retorna o InformaçõesPedidoDTO a partir do Desiger Pattern Builder (isso acontece por causa da anotação @Builder de Lombok na classe Informacoes
    PedidoDTO). Observe o método build() no final*/
    private InformacoesPedidoDTO converter(Pedido pedido)
    {
        return InformacoesPedidoDTO.builder().codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getPrecoTotal())
                .status(pedido.getStatus().name()) //Também pode ser usado o toString();
                .itens(this.converter(pedido.getItens()))
                .build();
    }
    /*Retorna uma List<> de InformacaoItemPedidoDTO a partir do Desiger Pattern Builder (isso acontece por causa da anotação @Builder de Lombok na classe Informacao
    ItemPedidoDTO), repare o método build() seguido de collect(Collectors.toList()) no final*/
    private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens)
    {
        if(CollectionUtils.isEmpty(itens)) return Collections.emptyList();

        return itens.stream().map(item -> InformacaoItemPedidoDTO.builder().descricaoProduto(item.getProduto().getDescricao())
                .precoUnitario(item.getProduto().getPrecoUnitario())
                .quantidade(item.getQuantidade()).build())
                .collect(Collectors.toList());
    }
}
