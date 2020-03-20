package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Logistics.
 */
@Entity
@Table(name = "logistics")
public class Logistics implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "logistics_id", nullable = false)
    private String logisticsId;

    @Column(name = "express_company")
    private String expressCompany;

    @Column(name = "express_number")
    private String expressNumber;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "start_position")
    private String startPosition;

    @Column(name = "end_position")
    private String endPosition;

    @Column(name = "current_position")
    private String currentPosition;

    @OneToOne(mappedBy = "id")
    @JsonIgnore
    private Order orderId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public Logistics logisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
        return this;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public Logistics expressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
        return this;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public Logistics expressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
        return this;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getStatus() {
        return status;
    }

    public Logistics status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public Logistics startPosition(String startPosition) {
        this.startPosition = startPosition;
        return this;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public Logistics endPosition(String endPosition) {
        this.endPosition = endPosition;
        return this;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public Logistics currentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
        return this;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Order getOrderId() {
        return orderId;
    }

    public Logistics orderId(Order order) {
        this.orderId = order;
        return this;
    }

    public void setOrderId(Order order) {
        this.orderId = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Logistics)) {
            return false;
        }
        return id != null && id.equals(((Logistics) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Logistics{" +
            "id=" + getId() +
            ", logisticsId='" + getLogisticsId() + "'" +
            ", expressCompany='" + getExpressCompany() + "'" +
            ", expressNumber='" + getExpressNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", startPosition='" + getStartPosition() + "'" +
            ", endPosition='" + getEndPosition() + "'" +
            ", currentPosition='" + getCurrentPosition() + "'" +
            "}";
    }
}
