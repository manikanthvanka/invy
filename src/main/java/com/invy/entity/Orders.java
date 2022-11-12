/**
 * 
 */
package com.invy.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author manikanth
 *
 *///OS
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@IdClass(value = Orders.class)
public class Orders implements Serializable{

	
	@Id
	@Column(name = "id")
	private Long id;
	
	@Id
	@Column(name = "order_id")
	private String orderId;
	
	@Id
	@Column(name = "sparepart_id")
	private Long sparePartId;
	@Id
	@Column(name = "sparepart_name")
	private String sparePartName;
	@Id
	@Column(name = "stock_required")
	private Integer stockRequired;
	@Id
	@Column(name = "ordered_by")
	private String orderedBy;
	@Id
	@Column(name = "orderedDate")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	LocalDateTime orderedDate=LocalDateTime.now();
	@Id
	@Column(name = "status")
	private String status="Created";

	@Id
	@Column(name = "updated_by")
	private String updatedBy;
	


}
