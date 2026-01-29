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
 * NoAttemptsMetricsResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class NoAttemptsMetricsResponse {

  private Integer oneDay;

  private Integer twoDays;

  private Integer threeDays;

  private Integer overThreeDays;

  public NoAttemptsMetricsResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public NoAttemptsMetricsResponse(Integer oneDay, Integer twoDays, Integer threeDays, Integer overThreeDays) {
    this.oneDay = oneDay;
    this.twoDays = twoDays;
    this.threeDays = threeDays;
    this.overThreeDays = overThreeDays;
  }

  public NoAttemptsMetricsResponse oneDay(Integer oneDay) {
    this.oneDay = oneDay;
    return this;
  }

  /**
   * Packages with 1 working day without attempt
   * @return oneDay
   */
  @NotNull 
  @Schema(name = "oneDay", description = "Packages with 1 working day without attempt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("oneDay")
  public Integer getOneDay() {
    return oneDay;
  }

  public void setOneDay(Integer oneDay) {
    this.oneDay = oneDay;
  }

  public NoAttemptsMetricsResponse twoDays(Integer twoDays) {
    this.twoDays = twoDays;
    return this;
  }

  /**
   * Packages with 2 working days without attempt
   * @return twoDays
   */
  @NotNull 
  @Schema(name = "twoDays", description = "Packages with 2 working days without attempt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("twoDays")
  public Integer getTwoDays() {
    return twoDays;
  }

  public void setTwoDays(Integer twoDays) {
    this.twoDays = twoDays;
  }

  public NoAttemptsMetricsResponse threeDays(Integer threeDays) {
    this.threeDays = threeDays;
    return this;
  }

  /**
   * Packages with 3 working days without attempt
   * @return threeDays
   */
  @NotNull 
  @Schema(name = "threeDays", description = "Packages with 3 working days without attempt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("threeDays")
  public Integer getThreeDays() {
    return threeDays;
  }

  public void setThreeDays(Integer threeDays) {
    this.threeDays = threeDays;
  }

  public NoAttemptsMetricsResponse overThreeDays(Integer overThreeDays) {
    this.overThreeDays = overThreeDays;
    return this;
  }

  /**
   * Packages with >3 working days without attempt
   * @return overThreeDays
   */
  @NotNull 
  @Schema(name = "overThreeDays", description = "Packages with >3 working days without attempt", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("overThreeDays")
  public Integer getOverThreeDays() {
    return overThreeDays;
  }

  public void setOverThreeDays(Integer overThreeDays) {
    this.overThreeDays = overThreeDays;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NoAttemptsMetricsResponse noAttemptsMetricsResponse = (NoAttemptsMetricsResponse) o;
    return Objects.equals(this.oneDay, noAttemptsMetricsResponse.oneDay) &&
        Objects.equals(this.twoDays, noAttemptsMetricsResponse.twoDays) &&
        Objects.equals(this.threeDays, noAttemptsMetricsResponse.threeDays) &&
        Objects.equals(this.overThreeDays, noAttemptsMetricsResponse.overThreeDays);
  }

  @Override
  public int hashCode() {
    return Objects.hash(oneDay, twoDays, threeDays, overThreeDays);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NoAttemptsMetricsResponse {\n");
    sb.append("    oneDay: ").append(toIndentedString(oneDay)).append("\n");
    sb.append("    twoDays: ").append(toIndentedString(twoDays)).append("\n");
    sb.append("    threeDays: ").append(toIndentedString(threeDays)).append("\n");
    sb.append("    overThreeDays: ").append(toIndentedString(overThreeDays)).append("\n");
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

    private NoAttemptsMetricsResponse instance;

    public Builder() {
      this(new NoAttemptsMetricsResponse());
    }

    protected Builder(NoAttemptsMetricsResponse instance) {
      this.instance = instance;
    }

    protected Builder copyOf(NoAttemptsMetricsResponse value) { 
      this.instance.setOneDay(value.oneDay);
      this.instance.setTwoDays(value.twoDays);
      this.instance.setThreeDays(value.threeDays);
      this.instance.setOverThreeDays(value.overThreeDays);
      return this;
    }

    public NoAttemptsMetricsResponse.Builder oneDay(Integer oneDay) {
      this.instance.oneDay(oneDay);
      return this;
    }
    
    public NoAttemptsMetricsResponse.Builder twoDays(Integer twoDays) {
      this.instance.twoDays(twoDays);
      return this;
    }
    
    public NoAttemptsMetricsResponse.Builder threeDays(Integer threeDays) {
      this.instance.threeDays(threeDays);
      return this;
    }
    
    public NoAttemptsMetricsResponse.Builder overThreeDays(Integer overThreeDays) {
      this.instance.overThreeDays(overThreeDays);
      return this;
    }
    
    /**
    * returns a built NoAttemptsMetricsResponse instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public NoAttemptsMetricsResponse build() {
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
  public static NoAttemptsMetricsResponse.Builder builder() {
    return new NoAttemptsMetricsResponse.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public NoAttemptsMetricsResponse.Builder toBuilder() {
    NoAttemptsMetricsResponse.Builder builder = new NoAttemptsMetricsResponse.Builder();
    return builder.copyOf(this);
  }

}

