package com.example.events.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.events.models.Message;
import com.example.events.repos.MessageRepo;

@Service
public class MessageService {
	private MessageRepo messageRepository;
	
	public MessageService (MessageRepo messageRepository) {

		this.messageRepository = messageRepository;
	}
	
//	public List<Message> allEvents() {
//    	return  messageRepository.findAll();
//	}

	public Message createMessage(Message b) {
		return  messageRepository.save(b);
	    }

}
