package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Demand} entity.
 */
public class DemandDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String demandId;

    @NotNull
    private Long quantity;


    private Long productIdId;

    private Long orderId;

    private String orderName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getProductIdId() {
        return productIdId;
    }

    public void setProductIdId(Long productId) {
        this.productIdId = productId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DemandDTO demandDTO = (DemandDTO) o;
        if (demandDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), demandDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DemandDTO{" +
            "id=" + getId() +
            ", demandId='" + getDemandId() + "'" +
            ", quantity=" + getQuantity() +
            ", productIdId=" + getProductIdId() +
            ", orderId=" + getOrderId() +
            ", orderName='" + getOrderName() + "'" +
            "}";
    }
}
