package de.evolvice.cars.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.evolvice.cars.entity.audit.UserDateAudit;
import io.swagger.annotations.ApiModelProperty;

/**
 * Car model
 * 
 * @author SayedBaladoh
 *
 */
@Entity
@Table(name = "models")
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Model extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated body type ID")
	private Long id;

	@NotBlank(message = "{name.notblank}")
	@Size(max = 100, message = "{name.size}")
	@Column(unique = true, length = 100, nullable = false)
	@ApiModelProperty(notes = "The model name")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ApiModelProperty(notes = "The brand of the model")
//	@JsonIgnore
	private Brand brand;

	@Min(value=1500, message="{year.from.notblank}")
	@Digits(integer = 4, fraction = 0, message = "{year.from.size}")
	@Column(name = "year_from", length = 4, nullable = false)
	@ApiModelProperty(notes = "The model start year")
	private int yearFrom;

	@Min(value=1500, message="{year.to.notblank}")
	@Digits(integer = 4, fraction = 0, message = "{year.to.size}")
	@Column(name = "year_To", length = 4, nullable = false)
	@ApiModelProperty(notes = "The model end year")
	private int yearTo;

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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getYearFrom() {
		return yearFrom;
	}

	public void setYearFrom(int yearFrom) {
		this.yearFrom = yearFrom;
	}

	public int getYearTo() {
		return yearTo;
	}

	public void setYearTo(int yearTo) {
		this.yearTo = yearTo;
	}

}
