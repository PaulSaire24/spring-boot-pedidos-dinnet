package com.dinnet.files.business;

import com.dinnet.files.model.RequestFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

    ResponseEntity<?> uploadFile(MultipartFile file, String idempontencyKey, String correlationId);
}
