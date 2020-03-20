package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.AssemblyLine} entity.
 */
public class AssemblyLineDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private Long capacity;

    @NotNull
    private Long planCapacity;

    @NotNull
    private String deviceId;


    private Long productId;

    private String productName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public Long getPlanCapacity() {
        return planCapacity;
    }

    public void setPlanCapacity(Long planCapacity) {
        this.planCapacity = planCapacity;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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

        AssemblyLineDTO assemblyLineDTO = (AssemblyLineDTO) o;
        if (assemblyLineDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assemblyLineDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssemblyLineDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", capacity=" + getCapacity() +
            ", planCapacity=" + getPlanCapacity() +
            ", deviceId='" + getDeviceId() + "'" +
            ", productId=" + getProductId() +
            ", productName='" + getProductName() + "'" +
            "}";
    }
}
