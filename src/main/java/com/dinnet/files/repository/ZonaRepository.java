package com.dinnet.files.repository;

import com.dinnet.files.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface ZonaRepository extends JpaRepository<Zona,String> {

    @Query(value = "select id from zonas" , nativeQuery = true)
    List<Map<String,String>> getIdZonas();
}
