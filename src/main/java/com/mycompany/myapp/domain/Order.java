package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(name = "jhi_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "order_id", nullable = false)
    private String orderId;

    @NotNull
    @Column(name = "customer", nullable = false)
    private String customer;

    @NotNull
    @Column(name = "begin_time", nullable = false)
    private ZonedDateTime beginTime;

    @NotNull
    @Column(name = "dead_line", nullable = false)
    private ZonedDateTime deadLine;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "details")
    private String details;

    @OneToOne
    @JoinColumn(unique = true)
    private Logistics id;

    @OneToMany(mappedBy = "order")
    private Set<Demand> demands = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("orders")
    private Factory factory;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public Order orderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomer() {
        return customer;
    }

    public Order customer(String customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public ZonedDateTime getBeginTime() {
        return beginTime;
    }

    public Order beginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
        return this;
    }

    public void setBeginTime(ZonedDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public ZonedDateTime getDeadLine() {
        return deadLine;
    }

    public Order deadLine(ZonedDateTime deadLine) {
        this.deadLine = deadLine;
        return this;
    }

    public void setDeadLine(ZonedDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public String getStatus() {
        return status;
    }

    public Order status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public Order details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Logistics getId() {
        return id;
    }

    public Order id(Logistics logistics) {
        this.id = logistics;
        return this;
    }

    public void setId(Logistics logistics) {
        this.id = logistics;
    }

    public Set<Demand> getDemands() {
        return demands;
    }

    public Order demands(Set<Demand> demands) {
        this.demands = demands;
        return this;
    }

    public Order addDemand(Demand demand) {
        this.demands.add(demand);
        demand.setOrder(this);
        return this;
    }

    public Order removeDemand(Demand demand) {
        this.demands.remove(demand);
        demand.setOrder(null);
        return this;
    }

    public void setDemands(Set<Demand> demands) {
        this.demands = demands;
    }

    public Factory getFactory() {
        return factory;
    }

    public Order factory(Factory factory) {
        this.factory = factory;
        return this;
    }

    public void setFactory(Factory factory) {
        this.factory = factory;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Order)) {
            return false;
        }
        return id != null && id.equals(((Order) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", orderId='" + getOrderId() + "'" +
            ", customer='" + getCustomer() + "'" +
            ", beginTime='" + getBeginTime() + "'" +
            ", deadLine='" + getDeadLine() + "'" +
            ", status='" + getStatus() + "'" +
            ", details='" + getDetails() + "'" +
            "}";
    }
}
