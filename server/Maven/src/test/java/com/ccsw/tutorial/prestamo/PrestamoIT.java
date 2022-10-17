package com.ccsw.tutorial.prestamo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.cliente.model.ClienteDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.prestamo.model.PrestamoDto;
import com.ccsw.tutorial.prestamo.model.PrestamoSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PrestamoIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/prestamo/";

    public static final Long DELETE_PREST_ID = 6L;
    public static final Long MODIFY_PREST_ID = 3L;

    private static final int TOTAL_PRESTS = 6;
    private static final int PAGE_SIZE = 5;

    private static final Long EXISTS_CLIENTE = 1L;
    private static final Long NOT_EXISTS_CLIENTE = 0L;

    private static final Long EXISTS_GAME = 1L;
    private static final Long NOT_EXISTS_GAME = 0L;

    private static final String EXISTS_DATE = "2016-01-12";
    private static final String NOT_EXISTS_DATE = "9999-12-01";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<Page<PrestamoDto>> responseTypePage = new ParameterizedTypeReference<Page<PrestamoDto>>() {
    };

    @Test
    public void findExistsTitleShouldReturnPrestamos() {

        int PRESTS_WITH_FILTER = 2;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?gameId=" + EXISTS_GAME, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findExistsClienteShouldReturnPrestamos() {

        int PRESTS_WITH_FILTER = 3;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?clienteId=" + EXISTS_CLIENTE, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(3, response.getBody().getContent().size());

    }

    @Test
    public void findExistDateShouldReturnPrestamos() {

        int PRESTS_WITH_FILTER = 2;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?searchDate=" + EXISTS_DATE, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findExistDateAndClienteAndGameShouldReturnPrestamos() {

        int PRESTS_WITH_FILTER = 1;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?searchDate=" + EXISTS_DATE + "&gameId=" + EXISTS_GAME
                        + "&clienteId=" + EXISTS_CLIENTE,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getContent().size());

    }

    @Test
    public void findNotExistDateOrClienteOrGameShouldReturnEmpty() {

        int PRESTS_WITH_FILTER = 0;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?searchDate=" + EXISTS_DATE + "&gameId=" + EXISTS_GAME
                        + "&clienteId=" + NOT_EXISTS_CLIENTE,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(0, response.getBody().getContent().size());

        response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?searchDate=" + EXISTS_DATE + "&gameId=" + NOT_EXISTS_GAME
                        + "&clienteId=" + EXISTS_CLIENTE,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(0, response.getBody().getContent().size());

        response = restTemplate.exchange(
                LOCALHOST + port + SERVICE_PATH + "?searchDate=" + NOT_EXISTS_DATE + "&gameId=" + EXISTS_GAME
                        + "&clienteId=" + EXISTS_CLIENTE,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PRESTS_WITH_FILTER, response.getBody().getTotalElements());
        assertEquals(0, response.getBody().getContent().size());

    }

    @Test
    public void saveWithoutIdShouldCreateNewPrestamo() {

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        PrestamoDto dto = new PrestamoDto();

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(1L);
        clienteDto.setName("NuevoCliente");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        GameDto gameDto = new GameDto();

        gameDto.setId(1L);
        gameDto.setTitle("NuevoJuego");
        gameDto.setAge("18");
        gameDto.setAuthor(authorDto);
        gameDto.setCategory(categoryDto);

        dto.setCliente(clienteDto);
        dto.setGame(gameDto);
        dto.setPrest_date(Date.valueOf("2024-12-01"));
        dto.setDevol_date(Date.valueOf("2024-12-13"));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH
                + "?searchDate=" + EXISTS_DATE + "&gameId=" + 1L + "&clienteId=" + NOT_EXISTS_CLIENTE, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(0, response.getBody().getContent().size());

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "?searchDate=2024-12-01", HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());

    }

    @Test
    public void saveWithBorrowedGameShouldReturnError() {

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

        PrestamoDto dto = new PrestamoDto();

        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setId(1L);
        clienteDto.setName("NuevoCliente");

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(1L);

        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        GameDto gameDto = new GameDto();

        gameDto.setId(1L);
        gameDto.setTitle("NuevoJuego");
        gameDto.setAge("18");
        gameDto.setAuthor(authorDto);
        gameDto.setCategory(categoryDto);

        dto.setCliente(clienteDto);
        dto.setGame(gameDto);
        dto.setPrest_date(Date.valueOf("2016-01-02"));
        dto.setDevol_date(Date.valueOf("2016-01-17"));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH
                + "?searchDate=" + EXISTS_DATE + "&gameId=" + 1L + "&clienteId=" + NOT_EXISTS_CLIENTE, HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(0, response.getBody().getContent().size());

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "?searchDate=2016-01-14", HttpMethod.POST,
                new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(2, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastResult() {

        int elementsCount = TOTAL_PRESTS - PAGE_SIZE;

        PrestamoSearchDto searchDto = new PrestamoSearchDto();
        searchDto.setPageable(PageRequest.of(1, PAGE_SIZE));

        ResponseEntity<Page<PrestamoDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_PRESTS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

}