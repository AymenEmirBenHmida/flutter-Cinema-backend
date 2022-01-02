package org.sid.entities;

import java.util.List;

public class chart {
List<String> films; 
List<Integer> ticketsSold;
public List<String> getFilms() {
	return films;
}
public void setFilms(List<String> films) {
	this.films = films;
}
public List<Integer> getTicketsSold() {
	return ticketsSold;
}
public void setTicketsSold(List<Integer> ticketsSold) {
	this.ticketsSold = ticketsSold;
}
public chart(List<String> films, List<Integer> ticketsSold) {
	super();
	this.films = films;
	this.ticketsSold = ticketsSold;
} 

}
