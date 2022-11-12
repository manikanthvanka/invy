/**
 * 
 */
package com.invy.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invy.entity.Orders;
import com.invy.entity.SpareParts;
import com.invy.entity.User;
import com.invy.exception.CustomException;
import com.invy.repository.OrdersRepository;
import com.invy.repository.SparePartRepository;
import com.invy.repository.UserRepository;

/**
 * @author manikanth
 *
 */

@Service
@Transactional(rollbackOn = Exception.class)
public class OrdersService {

	@Autowired
	SparePartRepository repo;

	@Autowired
	UserRepository userRepo;

	
	@Autowired
	OrdersRepository orderRepo;

	private boolean isSparePartIdValid(Long sparePartId) {

		Optional<SpareParts> s = repo.findById(sparePartId);

		return s.isPresent();
	}
	private boolean issupporttockAvailable(Long sparePartId,Integer stockRequired) {
		boolean returnValue = false;
		Optional<SpareParts> s = repo.findById(sparePartId);
		Integer stockAvailable =  s.isPresent()?s.get().getStockQuantity():0;
		Integer stockExists = stockAvailable - stockRequired;
		return stockExists<=0?returnValue:!returnValue;
	}
	
	

	public List<Orders> getAllOrders() {

		return orderRepo.findAll();
	}

	public List<Optional<Orders>> getOrder(String id) {

		return orderRepo.findByOrderId(id);
	}
	

	public boolean isAdmin(String userName) {
		boolean returnValue = true;
		Optional<User> userObject = userRepo.findRoleByName(userName);
		return !Strings.isEmpty(userName) && userObject.isPresent()
				&& userObject.get().getRole().equalsIgnoreCase("admin") ? returnValue : !returnValue;
	}

	@Transactional(rollbackOn = Exception.class)
	public String bookSparePart(List<Orders> ordersList) {
		
		Long id=orderRepo.getMaxId();
		id =id==0?1:++id;
		String orderId= getOrderIdBusiness(id);
		boolean valueToCheck =  false;
		for(Orders orders :  ordersList) {
			Long sparePartId =  orders.getSparePartId();
			Integer stockRequired =  orders.getStockRequired();
			String sparePartName = orders.getSparePartName();
			orders.setOrderId(orderId);
			
			orders.setId(id);
			if(isSparePartIdValid(sparePartId)==valueToCheck) {
				
				throw new CustomException(
				          "Sparepart doesn't exists",
				          "Sparepart "+sparePartName+" doesnt exists ",
				          "",
				          "Reachout to the support group",
				          "");
			}
			if(issupporttockAvailable(sparePartId,stockRequired)==valueToCheck) {
				throw new CustomException(
				          "Out of Stock",
				          "Sparepart "+sparePartName+" stock not available",
				          "",
				          "Reachout to the support group",
				          "Mail to support@test.com for more help");
				
			}
			
		orderRepo.save(orders);
		++id;
		}
		
		return String.valueOf(orderId);
	}
	
	public String getOrderIdBusiness(Long id){
	    DecimalFormat myFormatter = new DecimalFormat("ORD000000");
	    return myFormatter.format(id);
	}

}
