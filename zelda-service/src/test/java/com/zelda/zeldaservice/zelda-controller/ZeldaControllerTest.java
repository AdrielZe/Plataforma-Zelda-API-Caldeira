package com.zelda.zeldaservice.controller;

import com.zelda.zeldaservice.model.DadosGamesZeldaDTO;
import com.zelda.zeldaservice.model.ZeldaApi;
import com.zelda.zeldaservice.model.ZeldaApiList;
import com.zelda.zeldaservice.service.ZeldaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZeldaControllerTest {

    @InjectMocks
    ZeldaController zeldaController;

    @Mock
    private ZeldaService zeldaService;

    @Test
    public void testFindByIdGames() {
        zeldaService = mock(ZeldaService.class);
        ZeldaApi zeldaApi = new ZeldaApi();
        zeldaApi.setSuccess(true);
        zeldaApi.setData(new DadosGamesZeldaDTO("The Legend of Zelda", "Aventura", "Nintendo", "Nintendo", "2023-03-08", "1"));
        when(zeldaService.findByIdGames("1")).thenReturn(zeldaApi);

        ZeldaController zeldaController = new ZeldaController(zeldaService);
        ResponseEntity<ZeldaApi> responseEntity = zeldaController.findByIdGames("1");

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(zeldaApi, responseEntity.getBody());

        assertEquals("The Legend of Zelda", responseEntity.getBody().getData().getName());
        assertEquals("Aventura", responseEntity.getBody().getData().getDescription());
        assertEquals("Nintendo", responseEntity.getBody().getData().getDeveloper());
        assertEquals("Nintendo", responseEntity.getBody().getData().getPublisher());
        assertEquals("2023-03-08", responseEntity.getBody().getData().getReleased_date());
        assertEquals("1", responseEntity.getBody().getData().getId());
        assertEquals(true, responseEntity.getBody().getSuccess());
    }

    @Test
    public void testListGames() {
        ZeldaService zeldaService = mock(ZeldaService.class);
        ZeldaApiList zeldaApiList = new ZeldaApiList();
        zeldaApiList.setSuccess(true);
        zeldaApiList.setCount(10);
        zeldaApiList.setData(Arrays.asList(
                new DadosGamesZeldaDTO("The Legend of Zelda", "Aventura", "Nintendo", "Nintendo", "2023-03-08", "1"),
                new DadosGamesZeldaDTO("The Legend of Zelda: Breath of the Wild", "Aventura", "Nintendo", "Nintendo", "2017-03-03", "2"),
                new DadosGamesZeldaDTO("The Legend of Zelda: Ocarina of Time", "Aventura", "Nintendo", "Nintendo", "1998-03-20", "3")
        ));
        when(zeldaService.listGames()).thenReturn(zeldaApiList);

        ZeldaController zeldaController = new ZeldaController(zeldaService);
        ResponseEntity<ZeldaApiList> responseEntity = zeldaController.listGames();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(zeldaApiList, responseEntity.getBody());

        assertEquals(10, responseEntity.getBody().getCount());
        assertEquals(3, responseEntity.getBody().getData().size());
        assertEquals("The Legend of Zelda", responseEntity.getBody().getData().get(0).getName());
    }
}
