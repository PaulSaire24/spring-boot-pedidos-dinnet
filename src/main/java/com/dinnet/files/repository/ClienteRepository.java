package com.dinnet.files.repository;

import com.dinnet.files.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

    @Query(value = "select id from clientes" , nativeQuery = true)
    List<Map<String,String>> getIdClientes();
}
