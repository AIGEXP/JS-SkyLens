package com.jumia.skylens.http.in.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SuccessRateTargetRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class SuccessRateTargetRequest {

  private Double targetRate;

  public SuccessRateTargetRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SuccessRateTargetRequest(Double targetRate) {
    this.targetRate = targetRate;
  }

  public SuccessRateTargetRequest targetRate(Double targetRate) {
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
  @Schema(name = "targetRate", example = "0.95", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("targetRate")
  public Double getTargetRate() {
    return targetRate;
  }

  public void setTargetRate(Double targetRate) {
    this.targetRate = targetRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessRateTargetRequest successRateTargetRequest = (SuccessRateTargetRequest) o;
    return Objects.equals(this.targetRate, successRateTargetRequest.targetRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(targetRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessRateTargetRequest {\n");
    sb.append("    targetRate: ").append(toIndentedString(targetRate)).append("\n");
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

    private SuccessRateTargetRequest instance;

    public Builder() {
      this(new SuccessRateTargetRequest());
    }

    protected Builder(SuccessRateTargetRequest instance) {
      this.instance = instance;
    }

    protected Builder copyOf(SuccessRateTargetRequest value) { 
      this.instance.setTargetRate(value.targetRate);
      return this;
    }

    public SuccessRateTargetRequest.Builder targetRate(Double targetRate) {
      this.instance.targetRate(targetRate);
      return this;
    }
    
    /**
    * returns a built SuccessRateTargetRequest instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public SuccessRateTargetRequest build() {
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
  public static SuccessRateTargetRequest.Builder builder() {
    return new SuccessRateTargetRequest.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public SuccessRateTargetRequest.Builder toBuilder() {
    SuccessRateTargetRequest.Builder builder = new SuccessRateTargetRequest.Builder();
    return builder.copyOf(this);
  }

}

