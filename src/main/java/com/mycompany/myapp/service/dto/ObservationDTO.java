package com.mycompany.myapp.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Observation} entity.
 */
public class ObservationDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ZonedDateTime phenomenonTime;

    @NotNull
    private String result;

    @NotNull
    private ZonedDateTime resultTime;

    private String property;

    private String unit;


    private Long assemblylineId;

    private String assemblylineName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getPhenomenonTime() {
        return phenomenonTime;
    }

    public void setPhenomenonTime(ZonedDateTime phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ZonedDateTime getResultTime() {
        return resultTime;
    }

    public void setResultTime(ZonedDateTime resultTime) {
        this.resultTime = resultTime;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getAssemblylineId() {
        return assemblylineId;
    }

    public void setAssemblylineId(Long assemblyLineId) {
        this.assemblylineId = assemblyLineId;
    }

    public String getAssemblylineName() {
        return assemblylineName;
    }

    public void setAssemblylineName(String assemblyLineName) {
        this.assemblylineName = assemblyLineName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObservationDTO observationDTO = (ObservationDTO) o;
        if (observationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), observationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObservationDTO{" +
            "id=" + getId() +
            ", phenomenonTime='" + getPhenomenonTime() + "'" +
            ", result='" + getResult() + "'" +
            ", resultTime='" + getResultTime() + "'" +
            ", property='" + getProperty() + "'" +
            ", unit='" + getUnit() + "'" +
            ", assemblylineId=" + getAssemblylineId() +
            ", assemblylineName='" + getAssemblylineName() + "'" +
            "}";
    }
}
