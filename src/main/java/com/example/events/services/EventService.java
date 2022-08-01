package com.example.events.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.events.models.Event;
import com.example.events.models.Message;
import com.example.events.models.User;
import com.example.events.repos.EventRepo;
import com.example.events.repos.UserRepo;

@Service
public class EventService {
	private EventRepo eventRepository;
	public EventService (EventRepo eventRepository) {

		this.eventRepository = eventRepository;
	}
	
	public List<Event> allEvents() {
    	return  eventRepository.findAll();
	}

	public Event createEvent(Event b) {
		return  eventRepository.save(b);
	    }
	
	public Event findEvent(Long id) {
	    Optional<Event> optionalEvent =  eventRepository.findById(id);
	    return optionalEvent.isPresent()?  optionalEvent.get() : null;
	}
	
	public List<Event> findEventByState(String state) {
	    List<Event> events =  eventRepository.findByState(state);
	    return events;
	}
	
	public List<Event> findEventByStateIsNot(String state) {
	    List<Event> events =  eventRepository.findByStateIsNot(state);
	    return events;
	}
	
	public Event updateEvent(Long id,Event event) {
		Event current_event = findEvent(id);
		current_event.setDate(event.getDate());
		current_event.setName(event.getName());
		current_event.setLocation(event.getLocation());
		current_event.setState(event.getState());
		return  eventRepository.save(current_event);
	    }
	
	public Event cancelEvent(User user, Event event) {
		event.getPeople().remove(user);
		return  eventRepository.save(event); 
	}
	public Event joinEvent(User user, Event event) {
		event.getPeople().add(user);
		return  eventRepository.save(event); 
	}
	
	public Event addMessage (Message message, Event event) {
		event.getMessages().add(message);
		return  eventRepository.save(event);  
	}
	
	public void deleteEvent(Long id) {
		//Event current_event = findEvent(id);
		//current_event.getMessages().removeAll();
		//current_event.setMessages(null);
//		current_event.setUser(null);
//		current_event.setPeople(null);
		//eventRepository.save(current_event);
		eventRepository.deleteById(id);
	}
}
