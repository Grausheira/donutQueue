

package com.atrify.donutQueue.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QueueRepository extends JpaRepository<Queue, Long> {
	
	@Query("SELECT q FROM Queue q WHERE q.client.id < 1000 ORDER BY q.time ASC")
    public List<Queue> findPremiumOrders();
	@Query("SELECT q FROM Queue q WHERE q.client.id > 1000 ORDER BY q.time ASC")
    public List<Queue> findRegularOrders();

}
