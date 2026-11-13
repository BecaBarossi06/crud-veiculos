package com.umc.veiculos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.umc.veiculos.model.Veiculo;
import com.umc.veiculos.repository.VeiculoRepository;

@Service
public class VeiculoService {

    private final VeiculoRepository repository;

    // Injecao de dependencia pelo construtor
    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public List<Veiculo> listar() {
        return repository.findAll();
    }

    public List<Veiculo> buscarPorModelo(String modelo) {
        return repository.findByModeloContainingIgnoreCase(modelo.trim());
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Veiculo salvar(Veiculo veiculo) {
        // Regra de negocio: placa sempre salva em maiusculas e sem espacos
        veiculo.setPlaca(veiculo.getPlaca().trim().toUpperCase());
        veiculo.setModelo(veiculo.getModelo().trim());
        return repository.save(veiculo);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // Regra de negocio: nao permitir duas placas iguais
    public boolean placaEmUso(Veiculo veiculo) {
        if (veiculo.getPlaca() == null) {
            return false;
        }
        return repository.findByPlacaIgnoreCase(veiculo.getPlaca().trim())
                .filter(existente -> !existente.getId().equals(veiculo.getId()))
                .isPresent();
    }
}
