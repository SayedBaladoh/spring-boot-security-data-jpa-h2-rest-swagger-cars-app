package de.evolvice.cars.entity.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
/**
 * Provide created by and updated by auditing
 * 
 * @author sayed
 *
 */
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAudit extends DateAudit {

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @ApiModelProperty(notes = "The user who created")
    private Long createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    @ApiModelProperty(notes = "The user who updated")
    private Long updatedBy;

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
