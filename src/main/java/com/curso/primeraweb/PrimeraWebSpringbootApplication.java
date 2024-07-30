package com.curso.primeraweb;

import com.curso.primeraweb.entities.Persona;
import com.curso.primeraweb.repository.PersonaRepository;
import com.curso.primeraweb.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PrimeraWebSpringbootApplication implements CommandLineRunner {

	/*
	PRACTICA SOLO CON REPOSITORIO SIN MODELO|VISTA
	@Autowired
	private PersonaRepository personaRepository;
	*/
	//PRACTICA CON SERVICE Y REPOSITORIO SIN MODELO|VISTA
	@Autowired
	private PersonaService personaService;

	public static void main(String[] args) {
		SpringApplication.run(PrimeraWebSpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		PRACTICA CON REPOSITORIO  SIN MODELO|VISTA
		Persona p1 = new Persona();
		p1.setId(1L);
		p1.setNombre("ana");
		p1.setEdad(28);
		personaRepository.save(p1);
		personaRepository.save(new Persona(2L,"Miriam",28));

		System.out.println("numero de personas en la tabla "+ personaRepository.count());
		List<Persona> personas = personaRepository.findAll();
		personas.forEach(per -> System.out.println("Nombre: "+per.getNombre()));
		*/
		/*
		PRACTICA CON SERVICE Y REPOSITORIO  SIN MODELO|VISTA
		List<Persona> listP = new ArrayList<>();
		listP = personaService.obtenerTodas();
		listP.forEach(per -> System.out.println("Nombre: "+per.getNombre()+" | "+per.getEdad()));
		Persona busquedaXId = personaService.obtenerXId(1L);
		System.out.println("Datos de la persona a buscar: "+ busquedaXId.getId()+" | "+ busquedaXId.getNombre()+" | "+busquedaXId.getEdad());
		personaService.actualizarPersona(2L,new Persona(2L,"Ana Miriam",10));
		personaService.eliminarPersona(2L);
		personaService.crearPersona(new Persona(2L,"Bruno",10));
		System.out.println("6. Numero de personas en la tabla "+ personaService.numPersona());
		*/
	}
}
