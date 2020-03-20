package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Demand.
 */
@Entity
@Table(name = "demand")
public class Demand implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "demand_id", nullable = false)
    private String demandId;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @ManyToOne
    @JsonIgnoreProperties("ids")
    private Product productId;

    @ManyToOne
    @JsonIgnoreProperties("demands")
    private Order order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandId() {
        return demandId;
    }

    public Demand demandId(String demandId) {
        this.demandId = demandId;
        return this;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Demand quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Product getProductId() {
        return productId;
    }

    public Demand productId(Product product) {
        this.productId = product;
        return this;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }

    public Order getOrder() {
        return order;
    }

    public Demand order(Order order) {
        this.order = order;
        return this;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Demand)) {
            return false;
        }
        return id != null && id.equals(((Demand) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Demand{" +
            "id=" + getId() +
            ", demandId='" + getDemandId() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
