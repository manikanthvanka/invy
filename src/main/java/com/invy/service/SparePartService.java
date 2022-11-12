/**
 * 
 */
package com.invy.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invy.entity.SpareParts;
import com.invy.entity.User;
import com.invy.exception.CustomException;
import com.invy.repository.SparePartRepository;
import com.invy.repository.UserRepository;

/**
 * @author manikanth
 *
 */

@Service
@Transactional(rollbackOn = Exception.class)
public class SparePartService {

	@Autowired
	SparePartRepository repo;

	@Autowired
	UserRepository userRepo;

	@Transactional(rollbackOn = Exception.class)
	public SpareParts addProduct(SpareParts sparePartsEntity) {

		String createBy = sparePartsEntity.getCreatedByUserId();
		SpareParts r = null;
		if (isAdmin(createBy)) {
			r = repo.save(sparePartsEntity);
		} else {
			throw new CustomException("Invalid User", "No Admin priviliges to add sparepats", "",
					"Reachout to the support group", "Mail to support@test.com for more help");

		}

		return r;
	}

	@Transactional(rollbackOn = Exception.class)
	public SpareParts update(SpareParts sparePartsEntity) {

		String updatedBy = sparePartsEntity.getUpdatedBy();
		SpareParts returnSparePart = null;
		Optional<SpareParts> user = repo.findById(sparePartsEntity.getId());
		if (isAdmin(updatedBy) && user.isPresent()) {
			
			returnSparePart  = repo.save(updateValues(user.get(), sparePartsEntity));
		} else {
			throw new CustomException("Invalid User", "No Admin priviliges to update sparepats", "",
					"Reachout to the support group", "Mail to support@test.com for more help");
		}

		return returnSparePart;
	}

	private SpareParts updateValues(SpareParts sparePartsFromDb, SpareParts detailsToUpdate) {

		if (Objects.nonNull(detailsToUpdate.getName()) && !"".equalsIgnoreCase(detailsToUpdate.getName())) {
			sparePartsFromDb.setName(detailsToUpdate.getName());
		}
		if (Objects.nonNull(detailsToUpdate.getMaterialType())
				&& !"".equalsIgnoreCase(detailsToUpdate.getMaterialType())) {
			sparePartsFromDb.setMaterialType(detailsToUpdate.getMaterialType());
		}

		if (Objects.nonNull(detailsToUpdate.getManufactureDate()) && null != detailsToUpdate.getManufactureDate()) {
			sparePartsFromDb.setManufactureDate(detailsToUpdate.getManufactureDate());

		}

		if (Objects.nonNull(detailsToUpdate.getManufactureDate()) && null != detailsToUpdate.getManufactureDate()) {
			sparePartsFromDb.setManufactureDate(detailsToUpdate.getManufactureDate());

		}
		if (Objects.nonNull(detailsToUpdate.getBrand()) && !"".equalsIgnoreCase(detailsToUpdate.getBrand())) {
			sparePartsFromDb.setBrand(detailsToUpdate.getBrand());
		}

		if (Objects.nonNull(detailsToUpdate.getProductDesription())
				&& !"".equalsIgnoreCase(detailsToUpdate.getProductDesription())) {
			sparePartsFromDb.setProductDesription(detailsToUpdate.getProductDesription());
		}

		if (Objects.nonNull(detailsToUpdate.getActiveStatus())) {
			sparePartsFromDb.setActiveStatus(detailsToUpdate.getActiveStatus());
		}

		if (Objects.nonNull(detailsToUpdate.getUpdatedBy()) && !"".equalsIgnoreCase(detailsToUpdate.getUpdatedBy())) {
			sparePartsFromDb.setUpdatedBy(detailsToUpdate.getUpdatedBy());
		}

		if (Objects.nonNull(detailsToUpdate.getQuantityUnits())
				&& !"".equalsIgnoreCase(detailsToUpdate.getQuantityUnits())) {
			sparePartsFromDb.setQuantityUnits(detailsToUpdate.getQuantityUnits());
		}

		if (Objects.nonNull(detailsToUpdate.getStockQuantity()) && 0 != detailsToUpdate.getStockQuantity()) {
			sparePartsFromDb.setStockQuantity(detailsToUpdate.getStockQuantity());
		}

		if (Objects.nonNull(detailsToUpdate.getUnitPrice()) && 0 != detailsToUpdate.getUnitPrice()) {
			sparePartsFromDb.setUnitPrice(detailsToUpdate.getUnitPrice());
		}

		if (Objects.nonNull(detailsToUpdate.getManufactureCost()) && 0 != detailsToUpdate.getManufactureCost()) {
			sparePartsFromDb.setManufactureCost(detailsToUpdate.getManufactureCost());
		}

		if (Objects.nonNull(detailsToUpdate.getStockAvailableLocation())
				&& !"".equalsIgnoreCase(detailsToUpdate.getStockAvailableLocation())) {
			sparePartsFromDb.setStockAvailableLocation(detailsToUpdate.getStockAvailableLocation());
		}

		return sparePartsFromDb;
	}

	public List<SpareParts> getAll() {

		return repo.findAll();
	}

	public Optional<SpareParts> getSpareParts(Long id) {

		return repo.findById(id);
	}

	@Transactional(rollbackOn = Exception.class)
	public void deleteSparePartsById(long id) {
		repo.deleteById(id);
	}

	public boolean isAdmin(String userName) {
		boolean returnValue = true;
		Optional<User> userObject = userRepo.findRoleByName(userName);
		return !Strings.isEmpty(userName) && userObject.isPresent()
				&& userObject.get().getRole().equalsIgnoreCase("admin") ? returnValue : !returnValue;
	}

}
