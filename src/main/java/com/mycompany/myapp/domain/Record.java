package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Record.
 */
@Entity
@Table(name = "record")
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "daily_output")
    private Long dailyOutput;

    @NotNull
    @Column(name = "result_time", nullable = false)
    private ZonedDateTime resultTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("records")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDailyOutput() {
        return dailyOutput;
    }

    public Record dailyOutput(Long dailyOutput) {
        this.dailyOutput = dailyOutput;
        return this;
    }

    public void setDailyOutput(Long dailyOutput) {
        this.dailyOutput = dailyOutput;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public Record resultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
        return this;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public Product getProduct() {
        return product;
    }

    public Record product(Product product) {
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
        if (!(o instanceof Record)) {
            return false;
        }
        return id != null && id.equals(((Record) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Record{" +
            "id=" + getId() +
            ", dailyOutput=" + getDailyOutput() +
            ", resultTime='" + getResultTime() + "'" +
            "}";
    }
}
