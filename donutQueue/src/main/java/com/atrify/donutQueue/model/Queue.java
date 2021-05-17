package com.atrify.donutQueue.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Queue {
	
	@Id @GeneratedValue
	private Long id;	
	@OneToOne @MapsId
	private Client client;
	private Long ammount;
	@CreationTimestamp
	private Timestamp time;
	
	public Queue() {
		super();
	}
	public Queue(Client client, Long ammount) {
		super();
		this.client = client;
		this.ammount = ammount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Long getAmmount() {
		return ammount;
	}
	public void setAmmount(Long ammount) {
		this.ammount = ammount;
	}
	public Timestamp getTime() {
		return time;
	}
	
	public Long getTimeRemaining() {
		Date date= new Date();
	    long time = date.getTime();
	    Timestamp ts = new Timestamp(time);
		return 5 - (((ts.getTime()  - this.getTime().getTime()) / 1000) /60);
	}
	
	@
	Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ammount == null) ? 0 : ammount.hashCode());
		result = prime * result + ((client == null) ? 0 : client.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Queue other = (Queue) obj;
		if (ammount == null) {
			if (other.ammount != null)
				return false;
		} else if (!ammount.equals(other.ammount))
			return false;
		if (client == null) {
			if (other.client != null)
				return false;
		} else if (!client.equals(other.client))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Queue [id=" + id + ", client=" + client + ", ammount=" + ammount + ", time=" + time + "]";
	}
	
	
	
	

}
