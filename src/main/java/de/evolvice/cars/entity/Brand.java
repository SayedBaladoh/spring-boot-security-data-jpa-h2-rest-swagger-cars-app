package de.evolvice.cars.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.evolvice.cars.entity.audit.UserDateAudit;
import io.swagger.annotations.ApiModelProperty;

/**
 * Car brand or make or Manufacturer
 * 
 * @author SayedBaladoh
 *
 */
@Entity
@Table(name = "brands")
@DynamicUpdate(true)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Brand extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated body type ID")
	private Long id;

	@NotBlank(message = "{name.notblank}")
	@Size(max = 100, message = "{name.size}")
	@Column(unique = true, length = 100, nullable = false)
	@ApiModelProperty(notes = "The brand name")
	private String name;

	@Size(max = 250, message = "{website.size}")
	@Column(length = 250)
	@ApiModelProperty(notes = "The brand website")
	private String website;

	public Brand() {
		super();
	}

	public Brand(String name) {
		this.name = name;
	}

	public Brand(String name, String website) {
		super();
		this.name = name;
		this.website = website;
	}

	public Brand(Long id, String name, String website) {
		super();
		this.id = id;
		this.name = name;
		this.website = website;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

}
