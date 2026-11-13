package com.umc.veiculos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.umc.veiculos.model.Veiculo;
import com.umc.veiculos.service.VeiculoService;

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
    public String salvar(@ModelAttribute Veiculo veiculo) {
        service.salvar(veiculo);
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
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/veiculos";
    }
}
