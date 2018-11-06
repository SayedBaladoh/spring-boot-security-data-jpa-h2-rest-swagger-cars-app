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
import javax.validation.constraints.Min;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import de.evolvice.cars.entity.audit.UserDateAudit;
import io.swagger.annotations.ApiModelProperty;

/**
 * Car Body Specification Options
 * 
 * @author SayedBaladoh
 *
 */
@Entity
@Table(name = "specifications")
@DynamicUpdate(true)
public class Specification extends UserDateAudit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated body type ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "model_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@ApiModelProperty(notes = "The specification model")
	// @JsonIgnore
	private Model model;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "body_type_id", nullable = false)
	@ApiModelProperty(notes = "The specification body type")
	private BodyType bodyType;

	@Column(name = "doors_no", nullable = false, columnDefinition = "int default 0")
	@ApiModelProperty(notes = "The  number of doors")
	private int doorsNo;

	@Column(name = "seats_no", nullable = false, columnDefinition = "int default 0")
	@Min(value = 2, message = "{seats.no.min}")
	@ApiModelProperty(notes = "The number of seats")
	private int seatsNo;

	@Column(nullable = false, columnDefinition = "int default 0")
	@ApiModelProperty(notes = "The car length")
	private int length;

	@Column(nullable = false, columnDefinition = "int default 0")
	@ApiModelProperty(notes = "The car width")
	private int width;

	@Column(nullable = false, columnDefinition = "int default 0")
	@ApiModelProperty(notes = "The car height")
	private int height;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public BodyType getBodyType() {
		return bodyType;
	}

	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}

	public int getDoorsNo() {
		return doorsNo;
	}

	public void setDoorsNo(int doorsNo) {
		this.doorsNo = doorsNo;
	}

	public int getSeatsNo() {
		return seatsNo;
	}

	public void setSeatsNo(int seatsNo) {
		this.seatsNo = seatsNo;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
