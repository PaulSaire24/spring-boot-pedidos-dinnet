package com.dinnet.files.business;

import com.dinnet.files.model.RequestFile;
import com.dinnet.files.repository.ClienteRepository;
import com.dinnet.files.repository.FileRepository;
import com.dinnet.files.repository.ZonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationDataTest {

    @Mock
    private FileRepository fileRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ZonaRepository zonaRepository;

    @InjectMocks
    ValidationData validationData;

    @Test
    public void cleanDataAboutNumPedidoTest() {
        RequestFile resquest = new RequestFile();
        resquest.setNumeroPedido("P005");
        RequestFile resquest2 = new RequestFile();
        resquest2.setNumeroPedido("P006");
        List<RequestFile> requestList = List.of(resquest,resquest2);

        Map<String,String> pedido1 = Map.of("numero_pedido","P003");
        Map<String,String> pedido2 = Map.of("numero_pedido","P004");
        Map<String,String> pedido3 = Map.of("numero_pedido","P005");
        List<Map<String,String>> numberOrders =  List.of(pedido1, pedido2, pedido3);

        when(fileRepository.getListNumeroPedido()).thenReturn(numberOrders);

        Map<String,Object> result = validationData.cleanDataAboutNumPedido(requestList);

        assertEquals(1,result.get("count"));

    }

    @Test
    public void cleanDataAboutClientTest() {
        RequestFile resquest = new RequestFile();
        resquest.setClienteId("CLI-123");
        RequestFile resquest2 = new RequestFile();
        resquest2.setClienteId("CLI-999");
        RequestFile resquest3 = new RequestFile();
        resquest3.setClienteId("CLI-998");
        List<RequestFile> requestList = List.of(resquest,resquest2,resquest3);

        Map<String,String> client = Map.of("id","CLI-123");
        Map<String,String> client2 = Map.of("id","CLI-999");
        List<Map<String,String>> idClients =  List.of(client, client2);
        when(clienteRepository.getIdClientes()).thenReturn(idClients);

        Map<String,Object> result = validationData.cleanDataAboutCLient(requestList);

        assertEquals(1,result.get("count"));
    }

    @Test
    public void cleanDataAboutZonasTest() {
        RequestFile resquest = new RequestFile();
        resquest.setZonaEntrega("ZONA1");
        RequestFile resquest2 = new RequestFile();
        resquest2.setZonaEntrega("ZONA5");
        RequestFile resquest3 = new RequestFile();
        resquest3.setZonaEntrega("ZONA7");
        List<RequestFile> requestList = List.of(resquest,resquest2,resquest3);

        Map<String,String> zone = Map.of("id","ZONA1");
        Map<String,String> zone2 = Map.of("id","ZONA5");
        List<Map<String,String>> idZones =  List.of(zone, zone2);

        when(zonaRepository.getIdZonas()).thenReturn(idZones);

        Map<String,Object> result = validationData.cleanDataAboutZonas(requestList);

        assertEquals(1,result.get("count"));

    }

    @Test
    public void cleanDataAboutFechaEntregaTest() {
        RequestFile resquest = new RequestFile();
        resquest.setFechaEntrega(LocalDate.now());
        RequestFile resquest2 = new RequestFile();
        resquest2.setFechaEntrega(LocalDate.now());
        RequestFile resquest3 = new RequestFile();
        resquest3.setFechaEntrega(LocalDate.parse("2025-09-06"));
        List<RequestFile> requestList = List.of(resquest,resquest2,resquest3);



        Map<String,Object> result = validationData.cleanAboutFechaEntrega(requestList);

        assertEquals(1,result.get("count"));

    }


}