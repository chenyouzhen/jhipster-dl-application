package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Record} entity.
 */
public class RecordDTO implements Serializable {
    
    private Long id;

    private Long dailyOutput;

    @NotNull
    private ZonedDateTime resultTime;


    private Long productId;

    private String productName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDailyOutput() {
        return dailyOutput;
    }

    public void setDailyOutput(Long dailyOutput) {
        this.dailyOutput = dailyOutput;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecordDTO recordDTO = (RecordDTO) o;
        if (recordDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recordDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecordDTO{" +
            "id=" + getId() +
            ", dailyOutput=" + getDailyOutput() +
            ", resultTime='" + getResultTime() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
