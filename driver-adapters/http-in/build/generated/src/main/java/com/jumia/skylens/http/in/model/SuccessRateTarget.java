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
 * SuccessRateTarget
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class SuccessRateTarget {

  private Double targetRate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  public SuccessRateTarget() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SuccessRateTarget(Double targetRate, OffsetDateTime updatedAt) {
    this.targetRate = targetRate;
    this.updatedAt = updatedAt;
  }

  public SuccessRateTarget targetRate(Double targetRate) {
    this.targetRate = targetRate;
    return this;
  }

  /**
   * Get targetRate
   * minimum: 0
   * maximum: 1
   * @return targetRate
   */
  @NotNull @DecimalMin(value = "0") @DecimalMax(value = "1") 
  @Schema(name = "targetRate", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("targetRate")
  public Double getTargetRate() {
    return targetRate;
  }

  public void setTargetRate(Double targetRate) {
    this.targetRate = targetRate;
  }

  public SuccessRateTarget updatedAt(OffsetDateTime updatedAt) {
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
    SuccessRateTarget successRateTarget = (SuccessRateTarget) o;
    return Objects.equals(this.targetRate, successRateTarget.targetRate) &&
        Objects.equals(this.updatedAt, successRateTarget.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetRate, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessRateTarget {\n");
    sb.append("    targetRate: ").append(toIndentedString(targetRate)).append("\n");
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

    private SuccessRateTarget instance;

    public Builder() {
      this(new SuccessRateTarget());
    }

    protected Builder(SuccessRateTarget instance) {
      this.instance = instance;
    }

    protected Builder copyOf(SuccessRateTarget value) { 
      this.instance.setTargetRate(value.targetRate);
      this.instance.setUpdatedAt(value.updatedAt);
      return this;
    }

    public SuccessRateTarget.Builder targetRate(Double targetRate) {
      this.instance.targetRate(targetRate);
      return this;
    }
    
    public SuccessRateTarget.Builder updatedAt(OffsetDateTime updatedAt) {
      this.instance.updatedAt(updatedAt);
      return this;
    }
    
    /**
    * returns a built SuccessRateTarget instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public SuccessRateTarget build() {
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
  public static SuccessRateTarget.Builder builder() {
    return new SuccessRateTarget.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public SuccessRateTarget.Builder toBuilder() {
    SuccessRateTarget.Builder builder = new SuccessRateTarget.Builder();
    return builder.copyOf(this);
  }

}

