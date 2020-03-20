package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A AssemblyLine.
 */
@Entity
@Table(name = "assembly_line")
public class AssemblyLine implements Serializable {

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

    @NotNull
    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @NotNull
    @Column(name = "plan_capacity", nullable = false)
    private Long planCapacity;

    @NotNull
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @OneToMany(mappedBy = "assemblyline")
    private Set<Observation> observations = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("assemblylines")
    private Product product;

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

    public AssemblyLine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public AssemblyLine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCapacity() {
        return capacity;
    }

    public AssemblyLine capacity(Long capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getPlanCapacity() {
        return planCapacity;
    }

    public AssemblyLine planCapacity(Long planCapacity) {
        this.planCapacity = planCapacity;
        return this;
    }

    public void setPlanCapacity(Long planCapacity) {
        this.planCapacity = planCapacity;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public AssemblyLine deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Set<Observation> getObservations() {
        return observations;
    }

    public AssemblyLine observations(Set<Observation> observations) {
        this.observations = observations;
        return this;
    }

    public AssemblyLine addObservation(Observation observation) {
        this.observations.add(observation);
        observation.setAssemblyline(this);
        return this;
    }

    public AssemblyLine removeObservation(Observation observation) {
        this.observations.remove(observation);
        observation.setAssemblyline(null);
        return this;
    }

    public void setObservations(Set<Observation> observations) {
        this.observations = observations;
    }

    public Product getProduct() {
        return product;
    }

    public AssemblyLine product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssemblyLine)) {
            return false;
        }
        return id != null && id.equals(((AssemblyLine) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssemblyLine{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", capacity=" + getCapacity() +
            ", planCapacity=" + getPlanCapacity() +
            ", deviceId='" + getDeviceId() + "'" +
            "}";
    }
}
