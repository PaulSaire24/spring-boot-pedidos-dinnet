package com.dinnet.files.repository;

import com.dinnet.files.model.Idempotence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IdempotenceRepository extends JpaRepository<Idempotence, UUID> {

    Optional<Idempotence> findByIdempotencyKey(String idempotencyKey);
}
