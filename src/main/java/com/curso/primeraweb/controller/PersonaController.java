package com.curso.primeraweb.controller;

import com.curso.primeraweb.entities.Persona;
import com.curso.primeraweb.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/personas")
public class PersonaController {
    @Autowired
    private PersonaService personaService;
    //la clase/OBJETO model se utiliza para transferir objetos del controller a la vista

    //CREAR PERSONA
    @GetMapping
    public String listarPersonas(Model model) {
        List<Persona> personas = personaService.obtenerTodas();
        model.addAttribute("listaPersonas", personas);
        return "listar";
    }

    //llega desde el botton Nuevapersona en el tamplate listar cargado con un objeto que se llena con el formulario
    @GetMapping("/nuevaPersona")
    public String altaPersona(Model model) {
        model.addAttribute("persona", new Persona());
        // se especifica a donde llegar y tambien en el from en el template
        model.addAttribute("accion", "/personas/guardarPersona");
        return "altaPerForm";
    }

    //MODIFICAR
    // @ModelAttribute  recibe objeto persona desde el form ya va llenando
    @PostMapping("/guardarPersona")
    public String guardarPersona(@ModelAttribute Persona persona) {
        personaService.crearPersona(persona);
        return "redirect:/personas";
    }
    //para guardar al db
    @GetMapping("/mostrarFormModif/{id}")
    public String mostrarFormModif(@PathVariable Long id, @ModelAttribute Persona persona, Model model) {
        model.addAttribute("persona", persona);
        model.addAttribute("accion", "/personas/editarPersona/"+id);
        return "altaPerForm";
    }

    @PostMapping("/editarPersona/{id}")
    public String editarPersona( @PathVariable Long id, @ModelAttribute Persona persona) {
        personaService.actualizarPersona(id,persona);
        return "redirect:/personas";
    }

    /// ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable Long id) {
        personaService.eliminarPersona(id);
        return "redirect:/personas";
    }
}