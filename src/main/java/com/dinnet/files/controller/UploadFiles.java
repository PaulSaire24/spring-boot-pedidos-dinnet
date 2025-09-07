package com.dinnet.files.controller;

import com.dinnet.files.business.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadFiles {

    private final UploadService uploadService;

    @PostMapping("/pedidos/cargar")
    public ResponseEntity<?> sendFile(@RequestHeader(value = "Idempotency-Key", required = true) String idempontencyKey,
                                      @RequestHeader(value = "Correlation-Id", required = true) String correlationId,
                                      @RequestParam("file") MultipartFile file) {
        return uploadService.uploadFile(file,idempontencyKey,correlationId);
    }
}
