package io.github.RafaelA11y;

import io.github.RafaelA11y.domain.entity.Cliente;
import io.github.RafaelA11y.domain.entity.Pedido;
import io.github.RafaelA11y.domain.repository.Clientes;
import io.github.RafaelA11y.domain.repository.Pedidos;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/*A anotação @SpringBootAplication é a anotação responsável para definir esta classe como a classe responsável por iniciar a aplicação Sping Boot*/
@SpringBootApplication
public class VendasApplication
{
    /*A anotação @Bean serve para aplicar a injeção de dependência, repare que o método CommandLineRunner iniciar() não é chamado dentro do main(), note
    * também que seu objeto não foi declarado em momento nenhum (pois, iniciar() não é um método estático), ou seja, nem seu objeto foi declrado como
    * também o método inicar não foi explícitamente chamado, o Spring criou um objeto anônimo que chamou o método iniciar() por conta da anotação @Bean.
    * */
    @Bean
    CommandLineRunner iniciar(@Autowired Clientes clientes, @Autowired Pedidos pedidos)
    {
        return args -> {
            clientes.save(new Cliente("Rafael"));
            Cliente cliente = clientes.findById(1).get();

            Pedido pedido = new Pedido().adicionarCliente(cliente).adicionarDataPedido(LocalDate.now()).adicionarPreco(BigDecimal.valueOf(100L));
            pedidos.save(pedido);

            System.out.println("Imprimindo o cliente -> " + cliente);
            //Vai dar erro por tentar carregar os pedidos do objeto sendo que a busca está definida como preguiçosa, LAZY.
//            System.out.println("Imprimindo os pedidos de cliente -> " + cliente.getPedidos());
            System.out.print("Imprimindo os pedidos de cliente -> ");
            pedidos.findByCliente(cliente).stream().forEach(System.out::println);
//            pedidos.findByCliente(cliente.get()).stream().forEach(System.out::println);
        };

    }

    public static void main(String[] args)
    {
        /*Método responsável por iniciar a aplicação Spring Boot, recebe como parâmetro, a classe responsável pela inicialização da aplicação (a classe
        que possui a anotação @SpringBootAplication), e o array String fornecido pelo main().*/
        SpringApplication.run(VendasApplication.class, args);
    }
}
