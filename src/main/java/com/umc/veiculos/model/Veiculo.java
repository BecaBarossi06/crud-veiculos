package com.umc.veiculos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "veiculos")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo é obrigatório")
    @Size(min = 2, max = 60, message = "O modelo deve ter entre 2 e 60 caracteres")
    @Column(nullable = false, length = 60)
    private String modelo;

    @NotBlank(message = "A placa é obrigatória")
    @Pattern(regexp = "^[A-Za-z]{3}-?[0-9][A-Za-z0-9][0-9]{2}$",
             message = "Placa inválida. Use o formato ABC-1234 ou ABC1D23")
    @Column(nullable = false, unique = true, length = 8)
    private String placa;

    @NotNull(message = "O ano é obrigatório")
    @Min(value = 1900, message = "O ano deve ser maior ou igual a 1900")
    @Max(value = 2100, message = "O ano deve ser menor ou igual a 2100")
    @Column(nullable = false)
    private Integer ano;

    public Veiculo() {
    }

    public Veiculo(String modelo, String placa, Integer ano) {
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
