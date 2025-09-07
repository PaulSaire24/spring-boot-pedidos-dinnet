package com.dinnet.files.business;

import com.dinnet.files.model.RequestFile;
import com.dinnet.files.repository.ClienteRepository;
import com.dinnet.files.repository.FileRepository;
import com.dinnet.files.repository.ZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ValidationData {
    private final FileRepository fileRepository;
    private final ClienteRepository clienteRepository;
    private final ZonaRepository zonaRepository;

    public Map<String,Object> cleanDataAboutNumPedido(List<RequestFile> requests) {
        List<Map<String,String>> numberOrders  = fileRepository.getListNumeroPedido();
        List<String> pedidos =  numberOrders.stream()
                .map(p -> p.get("numero_pedido"))
                .toList();
        Integer count = countDataFailAboutNumPedido(pedidos,requests);
        List<RequestFile> requestfinal = requests.stream()
                .filter(p -> !pedidos.contains(p.getNumeroPedido()))
                .collect(Collectors.toList());

        Map<String,Object> mapResult =  new HashMap<>();
        mapResult.put("list",requestfinal);
        mapResult.put("count",count);

        return mapResult;
    }

    public Integer countDataFailAboutNumPedido(List<String> pedidos, List<RequestFile> requests) {
        return Math.toIntExact(requests.stream()
                .filter(p -> pedidos.contains(p.getNumeroPedido()))
                .count());
    }

    public Map<String,Object> cleanDataAboutCLient(List<RequestFile> requests) {
        List<Map<String,String>> idClientes  = clienteRepository.getIdClientes();
        List<String> clients = idClientes.stream()
                .map(p -> p.get("id"))
                .toList();

        Integer count = countDataFailAboutClient(clients,requests);
        List<RequestFile> requestFinal = requests.stream().filter(p -> clients.contains(p.getClienteId()))
                .collect(Collectors.toList());

        Map<String,Object> mapResult =  new HashMap<>();
        mapResult.put("list",requestFinal);
        mapResult.put("count",count);
        return mapResult;
    }

    public Integer countDataFailAboutClient(List<String> clients, List<RequestFile> requests) {
        return Math.toIntExact(requests.stream()
                .filter(p -> !clients.contains(p.getClienteId()))
                .count());
    }

    public Map<String,Object> cleanDataAboutZonas(List<RequestFile> requests) {
        List<Map<String,String>> idZonas  = zonaRepository.getIdZonas();
        List<String> zones = idZonas.stream().map(p -> p.get("id"))
                .toList();

        Integer count = countDataFailAboutZonas(zones,requests);
        List<RequestFile> requestFinal =  requests.stream().filter(p -> zones.contains(p.getZonaEntrega()))
                .collect(Collectors.toList());

        Map<String,Object> mapResult =  new HashMap<>();
        mapResult.put("list",requestFinal);
        mapResult.put("count",count);
        return mapResult;
    }

    public Integer countDataFailAboutZonas(List<String> zones, List<RequestFile> requests) {
        return Math.toIntExact(requests.stream()
                .filter(p -> !zones.contains(p.getZonaEntrega()))
                .count());
    }


    public Map<String,Object> cleanAboutFechaEntrega(List<RequestFile> requestFileList) {

        Integer count = countDataFailAboutFechaEntrega(requestFileList);

        List<RequestFile> request =  requestFileList.stream()
                            .filter(p -> p.getFechaEntrega().isEqual(LocalDate.now()))
                            .collect(Collectors.toList());

        Map<String,Object> mapResult =  new HashMap<>();
        mapResult.put("list",request);
        mapResult.put("count",count);
        return mapResult;

    }

    public Integer countDataFailAboutFechaEntrega(List<RequestFile> requests) {
        return Math.toIntExact(requests.stream()
                .filter(p -> !p.getFechaEntrega().isEqual(LocalDate.now()))
                .count());
    }
}
