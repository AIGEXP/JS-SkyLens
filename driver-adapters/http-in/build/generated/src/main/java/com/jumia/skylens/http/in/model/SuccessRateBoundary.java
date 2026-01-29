package com.jumia.skylens.http.in.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SuccessRateBoundary
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class SuccessRateBoundary {

  private Double warningThreshold;

  private Double criticalThreshold;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  public SuccessRateBoundary() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SuccessRateBoundary(Double warningThreshold, Double criticalThreshold, OffsetDateTime updatedAt) {
    this.warningThreshold = warningThreshold;
    this.criticalThreshold = criticalThreshold;
    this.updatedAt = updatedAt;
  }

  public SuccessRateBoundary warningThreshold(Double warningThreshold) {
    this.warningThreshold = warningThreshold;
    return this;
  }

  /**
   * Get warningThreshold
   * minimum: 0
   * maximum: 1
   * @return warningThreshold
   */
  @NotNull @DecimalMin(value = "0") @DecimalMax(value = "1") 
  @Schema(name = "warningThreshold", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("warningThreshold")
  public Double getWarningThreshold() {
    return warningThreshold;
  }

  public void setWarningThreshold(Double warningThreshold) {
    this.warningThreshold = warningThreshold;
  }

  public SuccessRateBoundary criticalThreshold(Double criticalThreshold) {
    this.criticalThreshold = criticalThreshold;
    return this;
  }

  /**
   * Get criticalThreshold
   * minimum: 0
   * maximum: 1
   * @return criticalThreshold
   */
  @NotNull @DecimalMin(value = "0") @DecimalMax(value = "1") 
  @Schema(name = "criticalThreshold", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("criticalThreshold")
  public Double getCriticalThreshold() {
    return criticalThreshold;
  }

  public void setCriticalThreshold(Double criticalThreshold) {
    this.criticalThreshold = criticalThreshold;
  }

  public SuccessRateBoundary updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
   */
  @NotNull @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessRateBoundary successRateBoundary = (SuccessRateBoundary) o;
    return Objects.equals(this.warningThreshold, successRateBoundary.warningThreshold) &&
        Objects.equals(this.criticalThreshold, successRateBoundary.criticalThreshold) &&
        Objects.equals(this.updatedAt, successRateBoundary.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(warningThreshold, criticalThreshold, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessRateBoundary {\n");
    sb.append("    warningThreshold: ").append(toIndentedString(warningThreshold)).append("\n");
    sb.append("    criticalThreshold: ").append(toIndentedString(criticalThreshold)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
  public static class Builder {

    private SuccessRateBoundary instance;

    public Builder() {
      this(new SuccessRateBoundary());
    }

    protected Builder(SuccessRateBoundary instance) {
      this.instance = instance;
    }

    protected Builder copyOf(SuccessRateBoundary value) { 
      this.instance.setWarningThreshold(value.warningThreshold);
      this.instance.setCriticalThreshold(value.criticalThreshold);
      this.instance.setUpdatedAt(value.updatedAt);
      return this;
    }

    public SuccessRateBoundary.Builder warningThreshold(Double warningThreshold) {
      this.instance.warningThreshold(warningThreshold);
      return this;
    }
    
    public SuccessRateBoundary.Builder criticalThreshold(Double criticalThreshold) {
      this.instance.criticalThreshold(criticalThreshold);
      return this;
    }
    
    public SuccessRateBoundary.Builder updatedAt(OffsetDateTime updatedAt) {
      this.instance.updatedAt(updatedAt);
      return this;
    }
    
    /**
    * returns a built SuccessRateBoundary instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public SuccessRateBoundary build() {
      try {
        return this.instance;
      } finally {
        // ensure that this.instance is not reused
        this.instance = null;
      }
    }

    @Override
    public String toString() {
      return getClass() + "=(" + instance + ")";
    }
  }

  /**
  * Create a builder with no initialized field (except for the default values).
  */
  public static SuccessRateBoundary.Builder builder() {
    return new SuccessRateBoundary.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public SuccessRateBoundary.Builder toBuilder() {
    SuccessRateBoundary.Builder builder = new SuccessRateBoundary.Builder();
    return builder.copyOf(this);
  }

}

