package com.dinnet.files.repository;

import com.dinnet.files.model.RequestFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FileRepository extends JpaRepository<RequestFile,String> {

    @Query(value = "select numero_pedido from pedidos" , nativeQuery = true)
    List<Map<String,String>> getListNumeroPedido();
}
