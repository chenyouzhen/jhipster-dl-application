package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Kpi} entity.
 */
public class KpiDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime resultTime;

    @NotNull
    private String type;

    @NotNull
    private String value;


    private Long factoryId;

    private String factoryName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(Long factoryId) {
        this.factoryId = factoryId;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        KpiDTO kpiDTO = (KpiDTO) o;
        if (kpiDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kpiDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "KpiDTO{" +
            "id=" + getId() +
            ", resultTime='" + getResultTime() + "'" +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", factoryId=" + getFactoryId() +
            ", factoryName='" + getFactoryName() + "'" +
            "}";
    }
}
