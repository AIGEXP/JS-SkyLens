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
 * SuccessRateBoundaryRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class SuccessRateBoundaryRequest {

  private Double warningThreshold;

  private Double criticalThreshold;

  public SuccessRateBoundaryRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SuccessRateBoundaryRequest(Double warningThreshold, Double criticalThreshold) {
    this.warningThreshold = warningThreshold;
    this.criticalThreshold = criticalThreshold;
  }

  public SuccessRateBoundaryRequest warningThreshold(Double warningThreshold) {
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
  @Schema(name = "warningThreshold", example = "0.9", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("warningThreshold")
  public Double getWarningThreshold() {
    return warningThreshold;
  }

  public void setWarningThreshold(Double warningThreshold) {
    this.warningThreshold = warningThreshold;
  }

  public SuccessRateBoundaryRequest criticalThreshold(Double criticalThreshold) {
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
  @Schema(name = "criticalThreshold", example = "0.8", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("criticalThreshold")
  public Double getCriticalThreshold() {
    return criticalThreshold;
  }

  public void setCriticalThreshold(Double criticalThreshold) {
    this.criticalThreshold = criticalThreshold;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessRateBoundaryRequest successRateBoundaryRequest = (SuccessRateBoundaryRequest) o;
    return Objects.equals(this.warningThreshold, successRateBoundaryRequest.warningThreshold) &&
        Objects.equals(this.criticalThreshold, successRateBoundaryRequest.criticalThreshold);
  }

  @Override
  public int hashCode() {
    return Objects.hash(warningThreshold, criticalThreshold);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessRateBoundaryRequest {\n");
    sb.append("    warningThreshold: ").append(toIndentedString(warningThreshold)).append("\n");
    sb.append("    criticalThreshold: ").append(toIndentedString(criticalThreshold)).append("\n");
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

    private SuccessRateBoundaryRequest instance;

    public Builder() {
      this(new SuccessRateBoundaryRequest());
    }

    protected Builder(SuccessRateBoundaryRequest instance) {
      this.instance = instance;
    }

    protected Builder copyOf(SuccessRateBoundaryRequest value) { 
      this.instance.setWarningThreshold(value.warningThreshold);
      this.instance.setCriticalThreshold(value.criticalThreshold);
      return this;
    }

    public SuccessRateBoundaryRequest.Builder warningThreshold(Double warningThreshold) {
      this.instance.warningThreshold(warningThreshold);
      return this;
    }
    
    public SuccessRateBoundaryRequest.Builder criticalThreshold(Double criticalThreshold) {
      this.instance.criticalThreshold(criticalThreshold);
      return this;
    }
    
    /**
    * returns a built SuccessRateBoundaryRequest instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public SuccessRateBoundaryRequest build() {
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
  public static SuccessRateBoundaryRequest.Builder builder() {
    return new SuccessRateBoundaryRequest.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public SuccessRateBoundaryRequest.Builder toBuilder() {
    SuccessRateBoundaryRequest.Builder builder = new SuccessRateBoundaryRequest.Builder();
    return builder.copyOf(this);
  }

}

