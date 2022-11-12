/**
 * 
 */
package com.invy.restcontroller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invy.entity.Orders;
import com.invy.service.OrdersService;

/**
 * @author manikanth
 *
 */

@RestController
@RequestMapping("/api/sparepart/book/")
public class BookingRestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrdersService orderService;

	@PostMapping
	public ResponseEntity<String> bookSparePart(@RequestBody List<Orders> ordersEntity) {
		String orderId = orderService.bookSparePart(ordersEntity);
		return new ResponseEntity<>(orderId, HttpStatus.CREATED);

	}


	@GetMapping
	public ResponseEntity<List<Orders>> getAllOrders() {

		List<Orders> ordersEntity = orderService.getAllOrders();

		if (ordersEntity.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(ordersEntity, HttpStatus.OK);

	}

	@GetMapping("{orderId}")
	public ResponseEntity<List<Optional<Orders>>> getOrderByOrderId(@PathVariable("orderId") String orderId,
			@RequestParam Map<String, String> reqParams) {



		List<Optional<Orders>> ordersEntity = orderService.getOrder(orderId);

		if (!ObjectUtils.isEmpty(ordersEntity) ) {
			return new ResponseEntity<>(ordersEntity, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
