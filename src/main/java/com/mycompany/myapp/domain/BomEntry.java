package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A BomEntry.
 */
@Entity
@Table(name = "bom_entry")
public class BomEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "material", nullable = false)
    private Long material;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @OneToOne
    @JoinColumn(unique = true)
    private Material id;

    @ManyToOne
    @JsonIgnoreProperties("ids")
    private Product productId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterial() {
        return material;
    }

    public BomEntry material(Long material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Long material) {
        this.material = material;
    }

    public Double getAmount() {
        return amount;
    }

    public BomEntry amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Material getId() {
        return id;
    }

    public BomEntry id(Material material) {
        this.id = material;
        return this;
    }

    public void setId(Material material) {
        this.id = material;
    }

    public Product getProductId() {
        return productId;
    }

    public BomEntry productId(Product product) {
        this.productId = product;
        return this;
    }

    public void setProductId(Product product) {
        this.productId = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BomEntry)) {
            return false;
        }
        return id != null && id.equals(((BomEntry) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BomEntry{" +
            "id=" + getId() +
            ", material=" + getMaterial() +
            ", amount=" + getAmount() +
            "}";
    }
}
