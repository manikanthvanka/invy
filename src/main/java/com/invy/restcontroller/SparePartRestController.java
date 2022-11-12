/**
 * 
 */
package com.invy.restcontroller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.invy.entity.SpareParts;
import com.invy.exception.CustomException;
import com.invy.service.SparePartService;
import com.invy.util.Constants;

/**
 * @author manikanth
 *
 */

@RestController
@RequestMapping("/api/spareparts/")
public class SparePartRestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private SparePartService sparepartService;

	@PostMapping
	public ResponseEntity<SpareParts> addPart(@RequestBody SpareParts sparePartDto) {
		SpareParts spareParts = sparepartService.addProduct(sparePartDto);
		return new ResponseEntity<>(spareParts, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<SpareParts> update(@RequestBody SpareParts sparePartsEntity) {
		SpareParts billDetails = sparepartService.update(sparePartsEntity);
		return new ResponseEntity<>(billDetails, HttpStatus.OK);

	}

	@GetMapping
	public ResponseEntity<List<SpareParts>> getAllSPareParts() {
		
		
		
		List<SpareParts> sparePartsEntitys = sparepartService.getAll();

		if (sparePartsEntitys.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(sparePartsEntitys, HttpStatus.OK);

	}

	@GetMapping("{id}")
	public ResponseEntity<SpareParts> getSpareParts(@PathVariable("id") Long id) {

	
		
		Optional<SpareParts> sparePartsEntity = sparepartService.getSpareParts(id);

		if (sparePartsEntity.isPresent()) {
			return new ResponseEntity<>(sparePartsEntity.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<HttpStatus> deleteSparePartsById(@PathVariable("id") long id,@RequestParam Map<String,String> reqParams) {
		

		if(Strings.isEmpty(reqParams.get("userId"))) {
			throw new CustomException(
			          "UserID is not passed",
			          "No UserID is passed",
			          "",
			          Constants.SUPPORT_GROUP,
			          Constants.SUPPORT_MAIL);
		}else {
			
			boolean isAdmin = sparepartService.isAdmin(reqParams.get("userId"));
			
			if(!isAdmin) {
				throw new CustomException(
				          Constants.INVALID_USER,
				          "No Admin priviliges to remove sparepats",
				          "",
				          Constants.SUPPORT_GROUP,
				          Constants.SUPPORT_MAIL);
			}
			
		}
		
		
		try {
			sparepartService.deleteSparePartsById(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
