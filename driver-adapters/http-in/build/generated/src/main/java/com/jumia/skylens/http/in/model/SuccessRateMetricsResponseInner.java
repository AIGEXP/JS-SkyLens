package com.jumia.skylens.http.in.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * SuccessRateMetricsResponseInner
 */

@JsonTypeName("SuccessRateMetricsResponse_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class SuccessRateMetricsResponseInner {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  private Integer packagesDelivered;

  private Integer packagesClosed;

  private @Nullable Double successRate = null;

  public SuccessRateMetricsResponseInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public SuccessRateMetricsResponseInner(LocalDate date, Integer packagesDelivered, Integer packagesClosed) {
    this.date = date;
    this.packagesDelivered = packagesDelivered;
    this.packagesClosed = packagesClosed;
  }

  public SuccessRateMetricsResponseInner date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public SuccessRateMetricsResponseInner packagesDelivered(Integer packagesDelivered) {
    this.packagesDelivered = packagesDelivered;
    return this;
  }

  /**
   * Get packagesDelivered
   * @return packagesDelivered
   */
  @NotNull 
  @Schema(name = "packagesDelivered", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("packagesDelivered")
  public Integer getPackagesDelivered() {
    return packagesDelivered;
  }

  public void setPackagesDelivered(Integer packagesDelivered) {
    this.packagesDelivered = packagesDelivered;
  }

  public SuccessRateMetricsResponseInner packagesClosed(Integer packagesClosed) {
    this.packagesClosed = packagesClosed;
    return this;
  }

  /**
   * Get packagesClosed
   * @return packagesClosed
   */
  @NotNull 
  @Schema(name = "packagesClosed", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("packagesClosed")
  public Integer getPackagesClosed() {
    return packagesClosed;
  }

  public void setPackagesClosed(Integer packagesClosed) {
    this.packagesClosed = packagesClosed;
  }

  public SuccessRateMetricsResponseInner successRate(@Nullable Double successRate) {
    this.successRate = successRate;
    return this;
  }

  /**
   * Get successRate
   * @return successRate
   */
  
  @Schema(name = "successRate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("successRate")
  public @Nullable Double getSuccessRate() {
    return successRate;
  }

  public void setSuccessRate(@Nullable Double successRate) {
    this.successRate = successRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SuccessRateMetricsResponseInner successRateMetricsResponseInner = (SuccessRateMetricsResponseInner) o;
    return Objects.equals(this.date, successRateMetricsResponseInner.date) &&
        Objects.equals(this.packagesDelivered, successRateMetricsResponseInner.packagesDelivered) &&
        Objects.equals(this.packagesClosed, successRateMetricsResponseInner.packagesClosed) &&
        Objects.equals(this.successRate, successRateMetricsResponseInner.successRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, packagesDelivered, packagesClosed, successRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SuccessRateMetricsResponseInner {\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    packagesDelivered: ").append(toIndentedString(packagesDelivered)).append("\n");
    sb.append("    packagesClosed: ").append(toIndentedString(packagesClosed)).append("\n");
    sb.append("    successRate: ").append(toIndentedString(successRate)).append("\n");
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

    private SuccessRateMetricsResponseInner instance;

    public Builder() {
      this(new SuccessRateMetricsResponseInner());
    }

    protected Builder(SuccessRateMetricsResponseInner instance) {
      this.instance = instance;
    }

    protected Builder copyOf(SuccessRateMetricsResponseInner value) { 
      this.instance.setDate(value.date);
      this.instance.setPackagesDelivered(value.packagesDelivered);
      this.instance.setPackagesClosed(value.packagesClosed);
      this.instance.setSuccessRate(value.successRate);
      return this;
    }

    public SuccessRateMetricsResponseInner.Builder date(LocalDate date) {
      this.instance.date(date);
      return this;
    }
    
    public SuccessRateMetricsResponseInner.Builder packagesDelivered(Integer packagesDelivered) {
      this.instance.packagesDelivered(packagesDelivered);
      return this;
    }
    
    public SuccessRateMetricsResponseInner.Builder packagesClosed(Integer packagesClosed) {
      this.instance.packagesClosed(packagesClosed);
      return this;
    }
    
    public SuccessRateMetricsResponseInner.Builder successRate(Double successRate) {
      this.instance.successRate(successRate);
      return this;
    }
    
    /**
    * returns a built SuccessRateMetricsResponseInner instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public SuccessRateMetricsResponseInner build() {
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
  public static SuccessRateMetricsResponseInner.Builder builder() {
    return new SuccessRateMetricsResponseInner.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public SuccessRateMetricsResponseInner.Builder toBuilder() {
    SuccessRateMetricsResponseInner.Builder builder = new SuccessRateMetricsResponseInner.Builder();
    return builder.copyOf(this);
  }

}

