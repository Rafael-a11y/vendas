package io.github.RafaelA11y.service;

import io.github.RafaelA11y.model.Cliente;
import io.github.RafaelA11y.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService
{
    ClientesRepository clientesRepository;
    //O próprio Spring vai chamar o construtor e criar o objeto ClienteService com um objeto ClientesRepository.
    @Autowired
    public ClientesService(ClientesRepository repository) {
        this.clientesRepository = repository;
    }

    public void salvarCliente(Cliente cliente)
    {
        validarCliente(cliente);
        clientesRepository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente)
    {
        //Valida o cliente;
    }
}
