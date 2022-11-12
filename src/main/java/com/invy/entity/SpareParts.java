/**
 * 
 */
package com.invy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author manikanth
 *
 *///OS
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spare_parts")
public class SpareParts {

	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "brand")
	private String brand;
	
	@Column(name = "description")
	private String productDesription;
	
	@Column(name = "materialType")
	private String materialType;

	@Column(name = "manufactureDate")
	@JsonFormat(pattern="yyyy-MM-dd")
	LocalDate manufactureDate;
	
	@Column(name = "stockAvailableLocation")
	private String stockAvailableLocation;
	
	@Column(name = "manfactureCost")
	private Float manufactureCost;
	
	@Column(name = "unitPrice")
	private Float unitPrice;
	
	@Column(name = "stockQuantity")
	private Integer stockQuantity;
	
	@Column(name = "quantityUnits")
	private String quantityUnits;
	
	//for logging/audit purpose

	
	@Column(name = "created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	LocalDateTime createdOn = LocalDateTime.now();
	
	@Column(name = "created_by_user_id")
	@NonNull
	private String createdByUserId;
	
	
	@Column(name = "updated_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@UpdateTimestamp
	LocalDateTime updateOn= LocalDateTime.now();
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	@Column(name = "activeStatus")
	private Boolean activeStatus =true;


}
