package org.sid.web;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;
import java.util.Map;

import javax.transaction.Transactional;

import org.sid.dao.CategorieRepository;
import org.sid.dao.FilmRepository;
import org.sid.dao.TicketRepository;
import org.sid.entities.Categorie;
import org.sid.entities.Film;
import org.sid.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	/*
	 * @Autowired private FilmRepository filmRepository;
	 * 
	 * @GetMapping("/listFilms") public List<Film> films(){ return
	 * filmRepository.findAll(); }
	 */
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private CategorieRepository categoryRepository;
	
	@GetMapping(path = "/imageFilm/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] image(@PathVariable(name = "id") Long id) throws Exception {

		Film f = filmRepository.findById(id).get();
		String photoName = f.getPhoto();
		File file = new File(System.getProperty("user.home") + "/cinema/images/" + photoName);
		System.out.println(file);
		// File file=new File(System.getProperty("D:/images/"+photoName));
		Path path = Paths.get(file.toURI());
		return Files.readAllBytes(path);
	}

	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm) {
		List<Ticket> listTickets = new ArrayList<>();
		ticketForm.getTickets().forEach(idTicket -> {
			//System.out.println(idTicket);
			Ticket ticket = ticketRepository.findById(idTicket).get();
			ticket.setNomClient(ticketForm.getNomClient());
			ticket.setReservee(true);
			ticket.setCodePaiement(ticketForm.codePaiement);
			ticketRepository.save(ticket);
			listTickets.add(ticket);
		});
		return listTickets;

	}

	@Data
	static class TicketForm {
		private String nomClient;
		private List<Long> tickets = new ArrayList<>();
		private int codePaiement;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/ticketsReserved")
	public List<Ticket> reserved() throws Exception {
		List<Ticket> listTickets = ticketRepository.findByTicketReservee(true);
	
		
		

		return listTickets;
		 
		
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/allFilms")
	public List<Film> filmsAll() throws Exception {	

		return filmRepository.findAll();		 
		
	}
	
	@RequestMapping(method = RequestMethod.POST,path ="/addFilm")
	public Film addFILM(@RequestBody Film flm ) {
	//	flm.setCategorie(flm.getCategorie());
		return filmRepository.save(flm);

	}
	
	@RequestMapping(method = RequestMethod.PUT,path ="/updateFilm")
	public Film updateFILM(@RequestBody Film flm ) {
	//	flm.setCategorie(flm.getCategorie());
		return filmRepository.save(flm);

	}
	@RequestMapping(method = RequestMethod.GET,path = "/allCategories")
	public List<Categorie> categoriesAll() throws Exception {	

		return categoryRepository.findAll();		 
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.PUT,path ="/updateTicket")
	public Ticket updateTicket(@RequestBody Ticket tick ) {
	//	flm.setCategorie(flm.getCategorie());
		return ticketRepository.save(tick);
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.DELETE,path = "/deleteTicket/{id}")
	public void deleteTicket(@PathVariable(name = "id") Long id) throws Exception {
		ticketRepository.deleteById(id);		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(method = RequestMethod.GET,path ="/chart")
	public Map<String, List<String>> chart() {
	//	flm.setCategorie(flm.getCategorie());
		List<String> categories = new ArrayList<String>();
		List<String> numberOfFilms = new ArrayList<String>();
		categoryRepository.findAll().forEach(categ ->{
			categories.add( categ.getName());
		});
		
		categories.forEach(categ->{
			numberOfFilms.add(String.valueOf(filmRepository.findCategorieByFilm(categ).size()));
		});
		HashMap<String, List<String>> map = new HashMap<>();
	    map.put("categories", categories);
	    map.put("numbers", numberOfFilms);
	   
	    return map;
	}

	
	
}
