package com.jumia.skylens.http.in.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jumia.skylens.http.in.model.DateRange;
import org.springframework.lang.Nullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DateRangeOption
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class DateRangeOption {

  private DateRange value;

  private String description;

  public DateRangeOption() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public DateRangeOption(DateRange value, String description) {
    this.value = value;
    this.description = description;
  }

  public DateRangeOption value(DateRange value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @NotNull @Valid 
  @Schema(name = "value", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("value")
  public DateRange getValue() {
    return value;
  }

  public void setValue(DateRange value) {
    this.value = value;
  }

  public DateRangeOption description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Human-readable label for display
   * @return description
   */
  @NotNull 
  @Schema(name = "description", description = "Human-readable label for display", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DateRangeOption dateRangeOption = (DateRangeOption) o;
    return Objects.equals(this.value, dateRangeOption.value) &&
        Objects.equals(this.description, dateRangeOption.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DateRangeOption {\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

    private DateRangeOption instance;

    public Builder() {
      this(new DateRangeOption());
    }

    protected Builder(DateRangeOption instance) {
      this.instance = instance;
    }

    protected Builder copyOf(DateRangeOption value) { 
      this.instance.setValue(value.value);
      this.instance.setDescription(value.description);
      return this;
    }

    public DateRangeOption.Builder value(DateRange value) {
      this.instance.value(value);
      return this;
    }
    
    public DateRangeOption.Builder description(String description) {
      this.instance.description(description);
      return this;
    }
    
    /**
    * returns a built DateRangeOption instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public DateRangeOption build() {
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
  public static DateRangeOption.Builder builder() {
    return new DateRangeOption.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public DateRangeOption.Builder toBuilder() {
    DateRangeOption.Builder builder = new DateRangeOption.Builder();
    return builder.copyOf(this);
  }

}

