package com.example.events.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.events.models.Event;

@Repository
public interface EventRepo extends CrudRepository<Event, Long>{
	List<Event> findAll();
	List<Event> findByState(String state);
	List<Event> findByStateIsNot(String state);
}
