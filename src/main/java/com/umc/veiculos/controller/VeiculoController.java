package com.umc.veiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.umc.veiculos.model.Veiculo;
import com.umc.veiculos.service.VeiculoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService service;

    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        if (busca != null && !busca.isBlank()) {
            model.addAttribute("veiculos", service.buscarPorModelo(busca));
        } else {
            model.addAttribute("veiculos", service.listar());
        }
        model.addAttribute("busca", busca);
        return "veiculos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("veiculo", new Veiculo());
        return "veiculos/form";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Veiculo veiculo, BindingResult result,
                         RedirectAttributes redirect) {
        // Regra de negocio: placa unica (so confere se o formato da placa esta valido)
        if (!result.hasFieldErrors("placa") && service.placaEmUso(veiculo)) {
            result.rejectValue("placa", "placa.duplicada",
                    "Já existe um veículo cadastrado com esta placa");
        }
        if (result.hasErrors()) {
            return "veiculos/form"; // volta ao formulario exibindo os erros
        }
        service.salvar(veiculo);
        redirect.addFlashAttribute("mensagem", "Veículo salvo com sucesso!");
        return "redirect:/veiculos"; // Post/Redirect/Get
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        return service.buscarPorId(id).map(veiculo -> {
            model.addAttribute("veiculo", veiculo);
            return "veiculos/form";
        }).orElse("redirect:/veiculos");
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("mensagem", "Veículo excluído com sucesso!");
        return "redirect:/veiculos";
    }
}
