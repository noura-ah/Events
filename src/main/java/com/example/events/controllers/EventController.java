package com.example.events.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.events.models.Event;
import com.example.events.models.Message;
import com.example.events.models.User;
import com.example.events.services.EventService;
import com.example.events.services.MessageService;
import com.example.events.services.UserService;


@Controller
public class EventController {
	private final EventService eventService;
	private final UserService userService;
	private final MessageService messageService;

	public EventController(EventService eventService,  UserService userService,MessageService messageService) {
		this.eventService = eventService;
		this.userService = userService;
		this.messageService = messageService;
	}
	
	//show events page for a user
	@GetMapping("/events")
	public String home(Model model,HttpSession session, RedirectAttributes redirectAttributes) { 
		
		if(session.getAttribute("user_id") == null) {
			redirectAttributes.addFlashAttribute("error", "you need to login/register first");
			return "redirect:/";
		}
		if (!model.containsAttribute("event")) {
			model.addAttribute("event",new Event());
		}
		Long id= (Long) session.getAttribute("user_id");
		User user = userService.findUser(id);
		model.addAttribute("user",user);
		
		List<Event> events = eventService.findEventByState(user.getState());
		List<Event> eventsNotInState = eventService.findEventByStateIsNot(user.getState());
		model.addAttribute("events",events);
		model.addAttribute("eventsNotInState",eventsNotInState);
		return "/events.jsp";
		
	}
	// add new event
	@PostMapping(value="events/new")
	public String add(Model model, @Valid @ModelAttribute("event") Event event, 
					BindingResult result, 
					RedirectAttributes redirectAttributes,
					HttpSession session
					) {
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("event",event);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.event",result);
			return "redirect:/events";
		} 
				
		Long id= (Long) session.getAttribute("user_id");
		User user = userService.findUser(id);
		// user created event
		event.setUser(user);
		
		// check if getPeople() is null
		if (event.getPeople() == null) {
			event.setPeople(new ArrayList<User>());
		}
		// add user to attendance list
		event.getPeople().add(user);
		
		eventService.createEvent(event);
		redirectAttributes.addFlashAttribute("success", "Event was created successfully");
		return "redirect:./";
			        
		}
	
	@GetMapping("events/{id}/remove")
	public String removeEvent (@PathVariable(value="id") Long id, HttpSession session) {
		Long user_id= (Long) session.getAttribute("user_id");
		User user = userService.findUser(user_id);
		Event event = eventService.findEvent(id);
		eventService.cancelEvent(user, event);
		return "redirect:/events";
		
	}
	@GetMapping("events/{id}/join")
	public String joinEvent (@PathVariable(value="id") Long id, HttpSession session) {
		Long user_id= (Long) session.getAttribute("user_id");
		User user = userService.findUser(user_id);
		Event event = eventService.findEvent(id);
		eventService.joinEvent(user, event);
		return "redirect:/events";
		
	}
	
	@GetMapping("events/{id}")
	public String showEvent (@PathVariable(value="id") Long id,Model model) {
		Event event = eventService.findEvent(id);
		List<Message> messages= event.getMessages();
		if(!model.containsAttribute("message")) {
			model.addAttribute("message", new Message());
		}
		model.addAttribute("messages",messages);
		model.addAttribute("event",event);
		return "/event_show.jsp";
	}
	
	// show the edit form page 
	@RequestMapping("events/{id}/edit")
		public String edit(@PathVariable("id") Long id, Model model) {
			if (!model.containsAttribute("event")) {
				Event event = eventService.findEvent(id);
				model.addAttribute("event", event);
			}
		    return "/event_edit.jsp";
		}
		    
	// edit 
	@PutMapping(value="events/{id}/update")
		public String update(@PathVariable("id") Long id,
				@Valid @ModelAttribute("event") Event event , 
				BindingResult result, RedirectAttributes redirectAttributes, HttpSession session) {
			if (result.hasErrors()) {
				redirectAttributes.addFlashAttribute("event",event);
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.event",result);
				return "redirect:/events/"+event.getId()+"/edit";
			}

			Long user_id=(Long) session.getAttribute("user_id");
			User user = userService.findUser(user_id);
			event.setUser(user);
			eventService.updateEvent(id,event);
			redirectAttributes.addFlashAttribute("success", "Event was edited successfully");
			return "redirect:./";
			}
	
	@DeleteMapping("/events/{id}/delete")
	public String deleteEvent(@PathVariable(value="id") Long id) {
		eventService.deleteEvent(id);
		return "redirect:/events";
	}
	
	@PostMapping("/events/{id}/comment")
	public String addComment(
			@Valid @ModelAttribute("message") Message message,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			Model model, HttpSession session, 
			@PathVariable("id") Long id) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("message",message);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.message",result);
			return "redirect:/events/"+id;
		}
		
		Long user_id=(Long) session.getAttribute("user_id");
		User user = userService.findUser(user_id);
		
		Long newid = Long.valueOf(id);
		Event event = eventService.findEvent(newid);
		
		message.setEvent(event);
		message.setUser(user);
		messageService.createMessage(message);
		return "redirect:/events/"+id;
		
	}
	
}
