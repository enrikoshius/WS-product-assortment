package mif.productassortment.domain;

import mif.productassortment.domain.enumerator.ProductStatus;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1871977717947082854L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date dateCreated;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private Date dateUpdated;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String description;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@PrePersist
	protected void onCreate() {
		dateCreated = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		dateUpdated = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ProductStatus getStatus() {
		return status;
	}

	public void setStatus(final ProductStatus status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(final Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(final Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}
}
