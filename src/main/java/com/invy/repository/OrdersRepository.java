/**
 * 
 */
package com.invy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.invy.entity.Orders;
public interface OrdersRepository  extends JpaRepository<Orders, Long>{

	@Query("SELECT coalesce(max(ch.id), 0) FROM Orders ch")
	Long getMaxId();

	List<Optional<Orders>> findByOrderId(String orderId);
}
