package com.dinnet.files.util;

import com.dinnet.files.model.RequestFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TransformationToRequestFile {

    public static RequestFile getListRequestFile(String[] data) {
        RequestFile requestFile = new RequestFile();
        requestFile.setNumeroPedido(data[0]);
        requestFile.setClienteId(data[1]);
        requestFile.setFechaEntrega(LocalDate.parse(data[2]));
        requestFile.setEstado(data[3]);
        requestFile.setZonaEntrega(data[4]);
        requestFile.setRequiereRefrigeracion(Boolean.valueOf(data[5]));
        requestFile.setCreated_at(LocalDateTime.now());
        return requestFile;
    }



}
