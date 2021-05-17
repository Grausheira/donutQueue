package com.atrify.donutQueue.exceptions;

public class QueueItemNotFoundExeption extends RuntimeException {

	private static final long serialVersionUID = -3613136086005726871L;

	public QueueItemNotFoundExeption(Long id) {
		super("Could not find Item "+id+" in queue");
	}

}
