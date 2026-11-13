package com.umc.veiculos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.umc.veiculos.model.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    // Query Method: busca por modelo, ignorando maiusculas/minusculas
    List<Veiculo> findByModeloContainingIgnoreCase(String modelo);

    // Usado para impedir placas duplicadas
    Optional<Veiculo> findByPlacaIgnoreCase(String placa);
}
