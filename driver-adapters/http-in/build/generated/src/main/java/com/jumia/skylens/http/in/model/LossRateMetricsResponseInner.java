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
 * LossRateMetricsResponseInner
 */

@JsonTypeName("LossRateMetricsResponse_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class LossRateMetricsResponseInner {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  private Integer packagesLost;

  private Integer packagesReceived;

  private @Nullable Double lossRate = null;

  public LossRateMetricsResponseInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LossRateMetricsResponseInner(LocalDate date, Integer packagesLost, Integer packagesReceived) {
    this.date = date;
    this.packagesLost = packagesLost;
    this.packagesReceived = packagesReceived;
  }

  public LossRateMetricsResponseInner date(LocalDate date) {
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

  public LossRateMetricsResponseInner packagesLost(Integer packagesLost) {
    this.packagesLost = packagesLost;
    return this;
  }

  /**
   * Get packagesLost
   * @return packagesLost
   */
  @NotNull 
  @Schema(name = "packagesLost", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("packagesLost")
  public Integer getPackagesLost() {
    return packagesLost;
  }

  public void setPackagesLost(Integer packagesLost) {
    this.packagesLost = packagesLost;
  }

  public LossRateMetricsResponseInner packagesReceived(Integer packagesReceived) {
    this.packagesReceived = packagesReceived;
    return this;
  }

  /**
   * Get packagesReceived
   * @return packagesReceived
   */
  @NotNull 
  @Schema(name = "packagesReceived", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("packagesReceived")
  public Integer getPackagesReceived() {
    return packagesReceived;
  }

  public void setPackagesReceived(Integer packagesReceived) {
    this.packagesReceived = packagesReceived;
  }

  public LossRateMetricsResponseInner lossRate(@Nullable Double lossRate) {
    this.lossRate = lossRate;
    return this;
  }

  /**
   * Get lossRate
   * @return lossRate
   */
  
  @Schema(name = "lossRate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("lossRate")
  public @Nullable Double getLossRate() {
    return lossRate;
  }

  public void setLossRate(@Nullable Double lossRate) {
    this.lossRate = lossRate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LossRateMetricsResponseInner lossRateMetricsResponseInner = (LossRateMetricsResponseInner) o;
    return Objects.equals(this.date, lossRateMetricsResponseInner.date) &&
        Objects.equals(this.packagesLost, lossRateMetricsResponseInner.packagesLost) &&
        Objects.equals(this.packagesReceived, lossRateMetricsResponseInner.packagesReceived) &&
        Objects.equals(this.lossRate, lossRateMetricsResponseInner.lossRate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, packagesLost, packagesReceived, lossRate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LossRateMetricsResponseInner {\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    packagesLost: ").append(toIndentedString(packagesLost)).append("\n");
    sb.append("    packagesReceived: ").append(toIndentedString(packagesReceived)).append("\n");
    sb.append("    lossRate: ").append(toIndentedString(lossRate)).append("\n");
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

    private LossRateMetricsResponseInner instance;

    public Builder() {
      this(new LossRateMetricsResponseInner());
    }

    protected Builder(LossRateMetricsResponseInner instance) {
      this.instance = instance;
    }

    protected Builder copyOf(LossRateMetricsResponseInner value) { 
      this.instance.setDate(value.date);
      this.instance.setPackagesLost(value.packagesLost);
      this.instance.setPackagesReceived(value.packagesReceived);
      this.instance.setLossRate(value.lossRate);
      return this;
    }

    public LossRateMetricsResponseInner.Builder date(LocalDate date) {
      this.instance.date(date);
      return this;
    }
    
    public LossRateMetricsResponseInner.Builder packagesLost(Integer packagesLost) {
      this.instance.packagesLost(packagesLost);
      return this;
    }
    
    public LossRateMetricsResponseInner.Builder packagesReceived(Integer packagesReceived) {
      this.instance.packagesReceived(packagesReceived);
      return this;
    }
    
    public LossRateMetricsResponseInner.Builder lossRate(Double lossRate) {
      this.instance.lossRate(lossRate);
      return this;
    }
    
    /**
    * returns a built LossRateMetricsResponseInner instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public LossRateMetricsResponseInner build() {
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
  public static LossRateMetricsResponseInner.Builder builder() {
    return new LossRateMetricsResponseInner.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public LossRateMetricsResponseInner.Builder toBuilder() {
    LossRateMetricsResponseInner.Builder builder = new LossRateMetricsResponseInner.Builder();
    return builder.copyOf(this);
  }

}

