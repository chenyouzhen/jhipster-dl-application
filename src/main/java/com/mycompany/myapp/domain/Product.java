package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.HashSet;
import java.util.Set;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(unique = true)
    private ProductStock id;

    @OneToMany(mappedBy = "productId")
    private Set<BomEntry> ids = new HashSet<>();

    @OneToMany(mappedBy = "productId")
    private Set<Demand> ids = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<AssemblyLine> assemblylines = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<Record> records = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("products")
    private Factory factory;

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

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public Product category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public Product model(String model) {
        this.model = model;
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public Product description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductStock getId() {
        return id;
    }

    public Product id(ProductStock productStock) {
        this.id = productStock;
        return this;
    }

    public void setId(ProductStock productStock) {
        this.id = productStock;
    }

    public Set<BomEntry> getIds() {
        return ids;
    }

    public Product ids(Set<BomEntry> bomEntries) {
        this.ids = bomEntries;
        return this;
    }

    public Product addId(BomEntry bomEntry) {
        this.ids.add(bomEntry);
        bomEntry.setProductId(this);
        return this;
    }

    public Product removeId(BomEntry bomEntry) {
        this.ids.remove(bomEntry);
        bomEntry.setProductId(null);
        return this;
    }

    public void setIds(Set<BomEntry> bomEntries) {
        this.ids = bomEntries;
    }

    public Set<Demand> getIds() {
        return ids;
    }

    public Product ids(Set<Demand> demands) {
        this.ids = demands;
        return this;
    }

    public Product addId(Demand demand) {
        this.ids.add(demand);
        demand.setProductId(this);
        return this;
    }

    public Product removeId(Demand demand) {
        this.ids.remove(demand);
        demand.setProductId(null);
        return this;
    }

    public void setIds(Set<Demand> demands) {
        this.ids = demands;
    }

    public Set<AssemblyLine> getAssemblylines() {
        return assemblylines;
    }

    public Product assemblylines(Set<AssemblyLine> assemblyLines) {
        this.assemblylines = assemblyLines;
        return this;
    }

    public Product addAssemblyline(AssemblyLine assemblyLine) {
        this.assemblylines.add(assemblyLine);
        assemblyLine.setProduct(this);
        return this;
    }

    public Product removeAssemblyline(AssemblyLine assemblyLine) {
        this.assemblylines.remove(assemblyLine);
        assemblyLine.setProduct(null);
        return this;
    }

    public void setAssemblylines(Set<AssemblyLine> assemblyLines) {
        this.assemblylines = assemblyLines;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public Product records(Set<Record> records) {
        this.records = records;
        return this;
    }

    public Product addRecord(Record record) {
        this.records.add(record);
        record.setProduct(this);
        return this;
    }

    public Product removeRecord(Record record) {
        this.records.remove(record);
        record.setProduct(null);
        return this;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Factory getFactory() {
        return factory;
    }

    public Product factory(Factory factory) {
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
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", category='" + getCategory() + "'" +
            ", model='" + getModel() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
