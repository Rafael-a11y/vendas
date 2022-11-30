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

    @Override
    @Transactional //Ou salva tudo com sucesso, ou nada é salvo. Caso algum erro aconteça, a aplicação dá um rollback, ou tudo é salvo corretamente
    public Pedido salvar(PedidoDTO dto) { //ou nada é salvo.
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

    @Override
    @Transactional
    public void atualizaStatusPedido(Integer id, StatusPedido statusPedido)
    {
        repository.findById(id).map(pedidoEncontrado -> {
            pedidoEncontrado.setStatus(statusPedido);
            return repository.save(pedidoEncontrado);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

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
