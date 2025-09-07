package com.dinnet.files.business;


import com.dinnet.files.dto.ErrorResponseDto;
import com.dinnet.files.dto.SucessResponseDto;
import com.dinnet.files.model.Idempotence;
import com.dinnet.files.model.RequestFile;
import com.dinnet.files.repository.FileRepository;
import com.dinnet.files.repository.IdempotenceRepository;
import com.dinnet.files.util.TransformationToRequestFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileToDatabase implements UploadService {

    private final FileRepository fileRepository;
    private final IdempotenceRepository idempotenceRepository;
    private final ValidationData validationData;

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file, String idempontencyKey, String correlationId) {

        if (file.isEmpty()) {
            ErrorResponseDto err =  new ErrorResponseDto();
            err.setCode(HttpStatus.BAD_REQUEST.value());
            err.setMessage("El archivo CSV está vacío.");
            err.setCorrelationId(correlationId);
            return ResponseEntity.badRequest().body(err);
        }

        try {
            String fileHash = calculateSHA256(file);
            Optional<Idempotence> idempotenceOptional = idempotenceRepository.findByIdempotencyKey(idempontencyKey);

            if (idempotenceOptional.isEmpty()) {
                List<RequestFile> requests = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                    String line;
                    int count = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] data = line.split(",");
                        requests.add(TransformationToRequestFile.getListRequestFile(data));
                        count++;
                    }
                    Map<String,Object> mapFechas = validationData.cleanAboutFechaEntrega(requests);
                    requests = (List<RequestFile>) mapFechas.get("list");
                    Integer counFailsFechaEntrega = (Integer) mapFechas.get("count");

                    Map<String,Object> mapNumPedido = validationData.cleanDataAboutNumPedido(requests);
                    requests = (List<RequestFile>) mapNumPedido.get("list");
                    Integer counFailsNumPedido = (Integer) mapNumPedido.get("count");

                    Map<String,Object> mapCLient =validationData.cleanDataAboutCLient(requests);
                    requests = (List<RequestFile>) mapCLient.get("list");
                    Integer counFailsCLient = (Integer) mapCLient.get("count");

                    Map<String,Object> mapZonas = validationData.cleanDataAboutZonas(requests);
                    requests = (List<RequestFile>) mapZonas.get("list");
                    Integer counFailsZonas = (Integer) mapZonas.get("count");

                    fileRepository.saveAll(requests);
                    Idempotence idempotence = new Idempotence();
                    idempotence.setIdempotencyKey(idempontencyKey);
                    idempotence.setArchivoHash(fileHash);
                    idempotence.setCreatedAt(LocalDateTime.now());
                    idempotenceRepository.save(idempotence);



                    Map<String,Integer> mapErrorFechas = Map.of("Error acerca de fecha de entrega", counFailsFechaEntrega,
                            "Error acerca Numero Pedidos repetidos", counFailsNumPedido,
                            "Error acerca Numero Clientes no existentes", counFailsCLient,
                            "Error acerca Zonas no existente", counFailsZonas);

                    SucessResponseDto res = new SucessResponseDto();
                    res.setCorrelationId(correlationId);
                    res.setTotalProcesados(count);
                    res.setGuardados(requests.size());
                    res.setConError(count-requests.size());
                    res.setErrorDetails(mapErrorFechas);
                    return ResponseEntity.status(HttpStatus.CREATED).body(res);
                }
            } else {
                ErrorResponseDto err =  new ErrorResponseDto();
                err.setCode(HttpStatus.BAD_REQUEST.value());
                err.setMessage("Idempotency key conflict");
                err.setCorrelationId(correlationId);
                return ResponseEntity.badRequest().body(err);
            }

        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String calculateSHA256(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(file.getBytes());
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
