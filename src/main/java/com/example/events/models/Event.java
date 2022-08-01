package com.example.events.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="events")
public class Event {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Size(min = 3, max = 200)
    private String name;
	
	@NotNull
    @Size(min = 3, max = 200)
    private String location;
	
	@NotNull
    @Size(min = 3, max = 200)
    private String state;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Future
    private Date date;
	
	
	// user created the event
	@ManyToOne
	@JoinColumn(name="user_id")
    private User user;
	
	// users joined the event
	@ManyToMany
	@JoinTable(
	        name = "people_joining_events", 
	        joinColumns = @JoinColumn(name = "event_id"), 
	        inverseJoinColumns = @JoinColumn(name = "user_id")
	    )
	private List<User> people ;
	
	// messages in the event
	@OneToMany(mappedBy="event" ,fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Message> messages;  
	
	// This will not allow the createdAt column to be updated after creation
    @Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
	
    
    public Event() {
    }
   
	@PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getPeople() {
		return people;
	}

	public void setPeople(List<User> people) {
		this.people = people;
	}

	public Long getId() {
		return id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	
    
    
	
}
