package com.mycompany.myapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Factory.
 */
@Entity
@Table(name = "factory")
public class Factory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "scale")
    private String scale;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @OneToMany(mappedBy = "factory")
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "factory")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "factory")
    private Set<Kpi> kpis = new HashSet<>();

    @OneToMany(mappedBy = "factory")
    private Set<Alarm> alarms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Factory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Factory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScale() {
        return scale;
    }

    public Factory scale(String scale) {
        this.scale = scale;
        return this;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getLocation() {
        return location;
    }

    public Factory location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Factory products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Factory addProduct(Product product) {
        this.products.add(product);
        product.setFactory(this);
        return this;
    }

    public Factory removeProduct(Product product) {
        this.products.remove(product);
        product.setFactory(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public Factory orders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public Factory addOrder(Order order) {
        this.orders.add(order);
        order.setFactory(this);
        return this;
    }

    public Factory removeOrder(Order order) {
        this.orders.remove(order);
        order.setFactory(null);
        return this;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Set<Kpi> getKpis() {
        return kpis;
    }

    public Factory kpis(Set<Kpi> kpis) {
        this.kpis = kpis;
        return this;
    }

    public Factory addKpi(Kpi kpi) {
        this.kpis.add(kpi);
        kpi.setFactory(this);
        return this;
    }

    public Factory removeKpi(Kpi kpi) {
        this.kpis.remove(kpi);
        kpi.setFactory(null);
        return this;
    }

    public void setKpis(Set<Kpi> kpis) {
        this.kpis = kpis;
    }

    public Set<Alarm> getAlarms() {
        return alarms;
    }

    public Factory alarms(Set<Alarm> alarms) {
        this.alarms = alarms;
        return this;
    }

    public Factory addAlarm(Alarm alarm) {
        this.alarms.add(alarm);
        alarm.setFactory(this);
        return this;
    }

    public Factory removeAlarm(Alarm alarm) {
        this.alarms.remove(alarm);
        alarm.setFactory(null);
        return this;
    }

    public void setAlarms(Set<Alarm> alarms) {
        this.alarms = alarms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Factory)) {
            return false;
        }
        return id != null && id.equals(((Factory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Factory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", scale='" + getScale() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
