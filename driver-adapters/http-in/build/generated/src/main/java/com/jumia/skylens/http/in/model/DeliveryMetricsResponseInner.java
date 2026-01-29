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
 * DeliveryMetricsResponseInner
 */

@JsonTypeName("DeliveryMetricsResponse_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class DeliveryMetricsResponseInner {

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate date;

  private Integer packagesDelivered;

  public DeliveryMetricsResponseInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DeliveryMetricsResponseInner(LocalDate date, Integer packagesDelivered) {
    this.date = date;
    this.packagesDelivered = packagesDelivered;
  }

  public DeliveryMetricsResponseInner date(LocalDate date) {
    this.date = date;
    return this;
  }

  /**
   * - DAILY: The day - WEEKLY: Week start (Monday) - MONTHLY: Month start (1st) 
   * @return date
   */
  @NotNull @Valid 
  @Schema(name = "date", description = "- DAILY: The day - WEEKLY: Week start (Monday) - MONTHLY: Month start (1st) ", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("date")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public DeliveryMetricsResponseInner packagesDelivered(Integer packagesDelivered) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryMetricsResponseInner deliveryMetricsResponseInner = (DeliveryMetricsResponseInner) o;
    return Objects.equals(this.date, deliveryMetricsResponseInner.date) &&
        Objects.equals(this.packagesDelivered, deliveryMetricsResponseInner.packagesDelivered);
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, packagesDelivered);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryMetricsResponseInner {\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    packagesDelivered: ").append(toIndentedString(packagesDelivered)).append("\n");
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

    private DeliveryMetricsResponseInner instance;

    public Builder() {
      this(new DeliveryMetricsResponseInner());
    }

    protected Builder(DeliveryMetricsResponseInner instance) {
      this.instance = instance;
    }

    protected Builder copyOf(DeliveryMetricsResponseInner value) { 
      this.instance.setDate(value.date);
      this.instance.setPackagesDelivered(value.packagesDelivered);
      return this;
    }

    public DeliveryMetricsResponseInner.Builder date(LocalDate date) {
      this.instance.date(date);
      return this;
    }
    
    public DeliveryMetricsResponseInner.Builder packagesDelivered(Integer packagesDelivered) {
      this.instance.packagesDelivered(packagesDelivered);
      return this;
    }
    
    /**
    * returns a built DeliveryMetricsResponseInner instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public DeliveryMetricsResponseInner build() {
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
  public static DeliveryMetricsResponseInner.Builder builder() {
    return new DeliveryMetricsResponseInner.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public DeliveryMetricsResponseInner.Builder toBuilder() {
    DeliveryMetricsResponseInner.Builder builder = new DeliveryMetricsResponseInner.Builder();
    return builder.copyOf(this);
  }

}

