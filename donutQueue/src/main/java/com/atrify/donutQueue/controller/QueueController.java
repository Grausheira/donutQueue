package com.atrify.donutQueue.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atrify.donutQueue.exceptions.QueueItemNotFoundExeption;
import com.atrify.donutQueue.model.Client;
import com.atrify.donutQueue.model.ClientRepository;
import com.atrify.donutQueue.model.Queue;
import com.atrify.donutQueue.model.QueueRepository;

@RestController
public class QueueController {
	
	private static final Logger log = LoggerFactory.getLogger(QueueController.class);
	
	private final QueueRepository queueRepository;
	private final ClientRepository clientRepository;
	
	public QueueController(QueueRepository queueRepository, ClientRepository clientRepository) {
		super();
		this.queueRepository = queueRepository;
		this.clientRepository = clientRepository;
	}
	/**
	 * This method is mapped to /addItem and expexts JSON as POST request.</br>
	 * It adds a new Item from the specified client and the order ammount.</br> 
	 * The message body must contain the client id and ammount of donuts as JSON data.</br>
	 * example body:
	 * {
     *	"client": {
     *  	"id": 10000
     *	},
     *		"ammount": 10
	 *}
	 * @param newItem
	 * @return Queue
	 */
	@PostMapping("addItem")
	Queue addItem(@RequestBody Queue newItem) {
		newItem.setClient( clientRepository.findById(newItem.getClient().getId()).get());
		return queueRepository.save(newItem);
		
	}
	/**
	 * This method is mapped to checkItem/{clientId} as GET request.</br>
	 * It will answer with JSON data of the request of the specified client.</br>
	 * The Path at {clientId} must contain the client id of the client.</br>
	 * @param clientId
	 * @return Queue
	 */
	@GetMapping("checkItem/{clientId}")
	Queue getItemByClient(@PathVariable Long clientId) {
		Example<Queue> example = Example.of(new Queue(new Client(clientId, null), null));
		return queueRepository.findOne(example).orElseThrow(() -> new QueueItemNotFoundExeption(clientId));
	}
	/**
	 * This method is mapped to checkItems as GET request.</br>
	 * It will return a list of all Items ordered by time of addition in ascending order.
	 * @return List<Queue>
	 */
	@GetMapping("checkItems")
	List<Queue> getAllItems() {
		return queueRepository.findAll(Sort.by(Sort.Direction.ASC, "time"));
	}
	/**
	 * This method is mapped to checkDelivery as GET request.</br>
	 * It will return a List<Queue> with items with a total ammount close as possible to 50 donuts prioritizing premium clients. 
	 * 
	 * @return List<Queue>
	 */
	@GetMapping("checkDelivery")
	List<Queue> getDelivery() {
		List<Queue> result = new ArrayList<Queue>();
		List<Queue> premium = queueRepository.findPremiumOrders();
		log.info("Premium "+premium);
		List<Queue> regular = queueRepository.findRegularOrders();
		Long donutCount = 0l;
		for(Queue element : premium) {
			if(donutCount + element.getAmmount() <= 50) {
				donutCount+= element.getAmmount();
				result.add(element);
			}
		}
		for(Queue element : regular) {
			if(donutCount + element.getAmmount() <= 50) {
				donutCount+= element.getAmmount();
				result.add(element);
			}
		}
		queueRepository.deleteAll(result);
		return result;
	}
	/**
	 * This method is mapped to deleteItem/{clientId} as DELETE request.</br>
	 * It will delete the order of the specified client.</br>
	 * The Path at {clientId} must contain the client id of the client.</br>
	 * @param clientId
	 */
	@DeleteMapping("deleteItem/{clientId}")
	void deleteItem(@PathVariable Long clientId) {
		Example<Queue> example = Example.of(new Queue(new Client(clientId, null), null));
		queueRepository.delete(queueRepository.findOne(example).orElseThrow(() -> new QueueItemNotFoundExeption(clientId)));
	}
	

}
