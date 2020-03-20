package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BomEntry} entity.
 */
public class BomEntryDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Long material;

    @NotNull
    private Double amount;


    private Long idId;

    private Long productIdId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMaterial() {
        return material;
    }

    public void setMaterial(Long material) {
        this.material = material;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getIdId() {
        return idId;
    }

    public void setIdId(Long materialId) {
        this.idId = materialId;
    }

    public Long getProductIdId() {
        return productIdId;
    }

    public void setProductIdId(Long productId) {
        this.productIdId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BomEntryDTO bomEntryDTO = (BomEntryDTO) o;
        if (bomEntryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), bomEntryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BomEntryDTO{" +
            "id=" + getId() +
            ", material=" + getMaterial() +
            ", amount=" + getAmount() +
            ", idId=" + getIdId() +
            ", productIdId=" + getProductIdId() +
            "}";
    }
}
