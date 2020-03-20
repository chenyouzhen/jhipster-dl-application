package com.mycompany.myapp.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Logistics} entity.
 */
public class LogisticsDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String logisticsId;

    private String expressCompany;

    private String expressNumber;

    @NotNull
    private String status;

    private String startPosition;

    private String endPosition;

    private String currentPosition;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(String startPosition) {
        this.startPosition = startPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LogisticsDTO logisticsDTO = (LogisticsDTO) o;
        if (logisticsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), logisticsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LogisticsDTO{" +
            "id=" + getId() +
            ", logisticsId='" + getLogisticsId() + "'" +
            ", expressCompany='" + getExpressCompany() + "'" +
            ", expressNumber='" + getExpressNumber() + "'" +
            ", status='" + getStatus() + "'" +
            ", startPosition='" + getStartPosition() + "'" +
            ", endPosition='" + getEndPosition() + "'" +
            ", currentPosition='" + getCurrentPosition() + "'" +
            "}";
    }
}
