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
 * Car Body Type
 * 
 * @author SayedBaladoh
 *
 */
@Entity
@Table(name = "body_types")
@DynamicUpdate(true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BodyType extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated body type ID")
	private Long id;

	@NotBlank(message = "{name.notblank}")
	@Size(max = 100, message = "{name.size}")
	@Column(unique = true, length = 100, nullable = false)
	@ApiModelProperty(notes = "The body type name")
	private String name;

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

}
