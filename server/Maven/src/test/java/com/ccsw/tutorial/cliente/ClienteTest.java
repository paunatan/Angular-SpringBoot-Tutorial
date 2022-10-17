package com.ccsw.tutorial.cliente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.cliente.model.Cliente;
import com.ccsw.tutorial.cliente.model.ClienteDto;

@ExtendWith(MockitoExtension.class)
public class ClienteTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Test
    public void findAllShouldReturnAllCategories() {

        List<Cliente> list = new ArrayList<>();
        list.add(mock(Cliente.class));

        when(clienteRepository.findAll()).thenReturn(list);

        List<Cliente> categories = clienteService.findAll();

        assertNotNull(categories);
        assertEquals(1, categories.size());
    }

    public static final String CLIENTE_NAME = "CAT1";

    @Test
    public void saveNotExistsClienteIdShouldInsert() {

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setName(CLIENTE_NAME);

        ArgumentCaptor<Cliente> cliente = ArgumentCaptor.forClass(Cliente.class);

        clienteService.save(null, clienteDto);

        verify(clienteRepository).save(cliente.capture());

        assertEquals(CLIENTE_NAME, cliente.getValue().getName());
    }

    public static final Long EXISTS_CLIENTE_ID = 1L;

    @Test
    public void saveExistsClienteIdShouldUpdate() {

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setName(CLIENTE_NAME);

        Cliente cliente = mock(Cliente.class);
        when(clienteRepository.findById(EXISTS_CLIENTE_ID)).thenReturn(Optional.of(cliente));

        clienteService.save(EXISTS_CLIENTE_ID, clienteDto);

        verify(clienteRepository).save(cliente);
    }

    @Test
    public void deleteExistsClienteIdShouldDelete() {

        clienteService.delete(EXISTS_CLIENTE_ID);

        verify(clienteRepository).deleteById(EXISTS_CLIENTE_ID);
    }

}
