package com.curso.primeraweb.service.impl;

import com.curso.primeraweb.entities.Persona;
import com.curso.primeraweb.repository.PersonaRepository;
import com.curso.primeraweb.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> obtenerTodas() {
        System.out.println("1. Todas las personas");
        return personaRepository.findAll();
    }

    @Override
    public Persona obtenerXId(Long id) {
        System.out.println("2. Persona con id: "+ id);
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    public Persona crearPersona(Persona persona) {
        System.out.println("3. Datos de la nueva persona: "+ persona.getId()+" | "+ persona.getNombre()+" | "+persona.getEdad());
        return personaRepository.save(persona);
    }

    @Override
    public Persona actualizarPersona(Long id, Persona persona) {
        System.out.println("4. Persona a actualizar: ");
        System.out.println("Id: "+ id);
        Persona personaInBD = personaRepository.findById(id).orElse(null);
        if ( personaInBD != null){
            personaInBD.setNombre(persona.getNombre());
            personaInBD.setEdad(persona.getEdad());
            System.out.println("nombre: "+ persona.getNombre());
            System.out.println("Edad: "+ persona.getEdad());
            return personaRepository.save(personaInBD);
        }
        return null;
    }

    @Override
    public void eliminarPersona(Long id) {
        System.out.println("5. Persona con id eliminada: "+ id);
        personaRepository.deleteById(id);
    }

    @Override
    public Long numPersona() {
        return personaRepository.count();
    }
}
