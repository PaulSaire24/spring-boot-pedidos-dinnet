package com.dinnet.files.business;

import com.dinnet.files.dto.ErrorResponseDto;
import com.dinnet.files.dto.SucessResponseDto;
import com.dinnet.files.model.Idempotence;
import com.dinnet.files.model.RequestFile;
import com.dinnet.files.repository.ClienteRepository;
import com.dinnet.files.repository.FileRepository;
import com.dinnet.files.repository.IdempotenceRepository;
import com.dinnet.files.repository.ZonaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadFileToDatabaseTest {

    @Mock
    private FileRepository fileRepository;
    @Mock
    private IdempotenceRepository idempotenceRepository;
    @Mock
    private ValidationData validationData;

    @InjectMocks
    UploadFileToDatabase uploadFileToDatabase;

    @Test
    public void uploadEmptyFile() {

        String idempontencyKey = "key-123";
        String correlationId = "anyHash";
        MultipartFile file = new MockMultipartFile(
                "archivo", "vacío.csv", "text/csv", new byte[0]);

        ResponseEntity<ErrorResponseDto> result = (ResponseEntity<ErrorResponseDto>) uploadFileToDatabase.uploadFile(file,idempontencyKey,correlationId);

        assertEquals("El archivo CSV está vacío.", result.getBody().getMessage());

    }

    @Test
    public void uploadFileAndidempotenceExisting() {

        String idempontencyKey = "key-123";
        String correlationId = "anyHash";
        MultipartFile file = new MockMultipartFile(
                "archivo", "vacío.csv", "text/csv", new byte[1]);
        when(idempotenceRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.of(new Idempotence()));

        ResponseEntity<ErrorResponseDto> result = (ResponseEntity<ErrorResponseDto>) uploadFileToDatabase.uploadFile(file,idempontencyKey,correlationId);

        assertEquals("Idempotency key conflict", result.getBody().getMessage());

    }

    @Test
    public void uploadFileAndidempotenceNoExisting() {

        String idempontencyKey = "key-123";
        String correlationId = "anyHash";
        String contenido = """
P005,CLI-123,2025-09-07,PENDIENTE,ZONA1,true
P006,CLI-999,2025-09-07,ENTREGADO,ZONA5,false
P004,CLI-999,2025-09-07,ENTREGADO,ZONA5,true
P007,CLI-998,2025-09-07,ENTREGADO,ZONA5,true
P008,CLI-123,2025-09-07,ENTREGADO,ZONA7,true
                """;
        MultipartFile file = new MockMultipartFile(
                "archivo", "vacío.csv", "text/csv", contenido.getBytes());

        Map<String,Object> mapFechas = Map.of("count",1);
        Map<String,Object> mapNumpedidos = Map.of("count",1);
        Map<String,Object> mapClient= Map.of("count",1);
        List<RequestFile> requestFileList = List.of(new RequestFile(),new RequestFile());
        Map<String,Object> mapZones = Map.of("count",1, "list", requestFileList);
        when(idempotenceRepository.findByIdempotencyKey("key-123")).thenReturn(Optional.empty());
        when(validationData.cleanAboutFechaEntrega(any())).thenReturn(mapFechas);
        when(validationData.cleanDataAboutNumPedido(any())).thenReturn(mapNumpedidos);
        when(validationData.cleanDataAboutCLient(any())).thenReturn(mapClient);
        when(validationData.cleanDataAboutZonas(any())).thenReturn(mapZones);
        when(fileRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(idempotenceRepository.save(any())).thenReturn(new Idempotence());

        ResponseEntity<SucessResponseDto> result = (ResponseEntity<SucessResponseDto>) uploadFileToDatabase.uploadFile(file,idempontencyKey,correlationId);

        assertEquals("anyHash", result.getBody().getCorrelationId());

    }


}