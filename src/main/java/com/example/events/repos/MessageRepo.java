package com.example.events.repos;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.events.models.Message;


@Repository
public interface MessageRepo extends CrudRepository<Message, Long>{

}

