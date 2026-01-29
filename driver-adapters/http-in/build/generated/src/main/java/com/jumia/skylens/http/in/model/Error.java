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
 * Error
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public class Error {

  private Long code;

  private String message;

  private @Nullable String codeName;

  private @Nullable String errors;

  public Error() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public Error(Long code, String message) {
    this.code = code;
    this.message = message;
  }

  public Error code(Long code) {
    this.code = code;
    return this;
  }

  /**
   * Get code
   * @return code
   */
  @NotNull 
  @Schema(name = "code", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("code")
  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }

  public Error message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Get message
   * @return message
   */
  @NotNull 
  @Schema(name = "message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Error codeName(@Nullable String codeName) {
    this.codeName = codeName;
    return this;
  }

  /**
   * Get codeName
   * @return codeName
   */
  
  @Schema(name = "codeName", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("codeName")
  public @Nullable String getCodeName() {
    return codeName;
  }

  public void setCodeName(@Nullable String codeName) {
    this.codeName = codeName;
  }

  public Error errors(@Nullable String errors) {
    this.errors = errors;
    return this;
  }

  /**
   * Get errors
   * @return errors
   */
  
  @Schema(name = "errors", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("errors")
  public @Nullable String getErrors() {
    return errors;
  }

  public void setErrors(@Nullable String errors) {
    this.errors = errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.code, error.code) &&
        Objects.equals(this.message, error.message) &&
        Objects.equals(this.codeName, error.codeName) &&
        Objects.equals(this.errors, error.errors);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, codeName, errors);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    codeName: ").append(toIndentedString(codeName)).append("\n");
    sb.append("    errors: ").append(toIndentedString(errors)).append("\n");
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

    private Error instance;

    public Builder() {
      this(new Error());
    }

    protected Builder(Error instance) {
      this.instance = instance;
    }

    protected Builder copyOf(Error value) { 
      this.instance.setCode(value.code);
      this.instance.setMessage(value.message);
      this.instance.setCodeName(value.codeName);
      this.instance.setErrors(value.errors);
      return this;
    }

    public Error.Builder code(Long code) {
      this.instance.code(code);
      return this;
    }
    
    public Error.Builder message(String message) {
      this.instance.message(message);
      return this;
    }
    
    public Error.Builder codeName(String codeName) {
      this.instance.codeName(codeName);
      return this;
    }
    
    public Error.Builder errors(String errors) {
      this.instance.errors(errors);
      return this;
    }
    
    /**
    * returns a built Error instance.
    *
    * The builder is not reusable (NullPointerException)
    */
    public Error build() {
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
  public static Error.Builder builder() {
    return new Error.Builder();
  }

  /**
  * Create a builder with a shallow copy of this instance.
  */
  public Error.Builder toBuilder() {
    Error.Builder builder = new Error.Builder();
    return builder.copyOf(this);
  }

}

