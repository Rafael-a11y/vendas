package io.github.RafaelA11y.service.impl;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.entity.ItemPedido;
import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.domain.entity.Produto;
import io.github.RafaelA11y.domain.enums.StatusPedido;
import io.github.RafaelA11y.domain.repository.Clientes;
import io.github.RafaelA11y.domain.repository.ItensPedido;
import io.github.RafaelA11y.domain.repository.Pedidos;
import io.github.RafaelA11y.domain.repository.Produtos;
import io.github.RafaelA11y.exception.PedidoNaoEncontradoException;
import io.github.RafaelA11y.exception.RegraNegocioException;
import io.github.RafaelA11y.rest.dto.ItemPedidoDTO;
import io.github.RafaelA11y.rest.dto.PedidoDTO;
import io.github.RafaelA11y.service.PedidoService;
import io.github.RafaelA11y.exception.PedidoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// O que acontece se eu remover esta anotação?
@Service
@RequiredArgsConstructor //Gera um construtor com todos os campos obrigatórios (final).
public class PedidoServiceImpl implements PedidoService
{
    // Repository de Pedido
    private final Pedidos repository;
    private final Clientes clientesRepository; //Repository de cliente
    private final Produtos produtosRepository; //Repository de produto
    private final ItensPedido itensPedidoRepository; //Repository de itensPedidos

    /*A operação recebe um PedidoDTO e retorna um Pedido salvo na base de dados. A anotação @Transactional serve para que a transação no banco de dados
    * somente seja efetuada se tudo ocorrer conforme o esperado, pois neste método, estamos persistindo 2 novos registros no banco de dados, o pedido,
    * o novo item, por isso é necessário garantir que os dados sejam processados apenas se tudo der certo, e não causar inconsistência de dados como
    * registrar um pedido associado a itens que não estão registrados no banco. Após o pedido ser salvo no banco de dados, os seu itens são salvos
    * no banco, pois como um pedido tem vários itens, significa que a foreign key estará presente na tabela ITEM_PEDIDO, referenciando a tabela PEDIDO,
    * e assim sendo não é possível registrar um itemPedido sem antes registrar um pedido.*/
    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de cliente inválido: " + idCliente));

        Pedido pedido = new Pedido();
        pedido.setPrecoTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        repository.save(pedido);
        itensPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    /*Recebe um id e um StatusPedido, procura o pedido na base de dados pelo id informado e se este existir, seta seu status com o valor de StatusPedido
    * informado e chama o save() para salvar a alteração no banco de dados, caso contrário chama a excpetion PedidoNaoEncontradoException.*/
    @Override
    @Transactional
    public void atualizaStatusPedido(Integer id, StatusPedido statusPedido)
    {
        repository.findById(id).map(pedidoEncontrado -> {
            pedidoEncontrado.setStatus(statusPedido);
            return repository.save(pedidoEncontrado);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    /*Método privado que serve para ser chamado por public Pedido salvar(PedidoDTO dto), converte a List<ItemPedidoDTO> em List<ItemPedido> associada
    * ao pedido passado por parâmetro e ao produto recuperado por pesquisa no banco de dados, essa operação acontece a partir de um map().*/
    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens)
    {
        if(itens.isEmpty()) throw new RegraNegocioException("Não é possível realizar um pedido sem itens");

        return itens.stream().map(dto -> {Integer idProduto = dto.getProduto();
                                            Produto produto = produtosRepository.findById(idProduto).
                                                    orElseThrow(() -> new RegraNegocioException("Código de produto inválido " + idProduto));
                                            ItemPedido itemPedido = new ItemPedido();
                                            itemPedido.setQuantidade(dto.getQuantidade());
                                            itemPedido.setPedido(pedido);
                                            itemPedido.setProduto(produto);
                                            return itemPedido;
                                        }).collect(Collectors.toList());
    }

    /*Retorna um pedido pelo seu id (na verdade um Optional de Pedido, pois pode ser que a consulta não encontre nenhum Pedido com o id passado)*/
    @Override
    public Optional <Pedido> obterPedidoCompleto(Integer id)
    {
        return repository.findByIdFetchItens(id);
    }
}
