package com.jumia.skylens.http.in.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Date range selection affects response array size and date granularity:  | Value             | Array Size | Granularity | Date Represents     | |-------------------|------------|-------------|---------------------| | CURRENT_WEEK      | 7          | Daily       | Each day            | | LAST_WEEK         | 7          | Daily       | Each day            | | LAST_FOUR_WEEKS   | 4          | Weekly      | Week start (Monday) | | LAST_THREE_MONTHS | 3          | Monthly     | Month start (1st)   | 
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-01-29T11:59:54.251057115Z[Europe/Lisbon]", comments = "Generator version: 7.18.0")
public enum DateRange {
  
  CURRENT_WEEK("CURRENT_WEEK"),
  
  LAST_WEEK("LAST_WEEK"),
  
  LAST_FOUR_WEEKS("LAST_FOUR_WEEKS"),
  
  LAST_THREE_MONTHS("LAST_THREE_MONTHS");

  private final String value;

  DateRange(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static DateRange fromValue(String value) {
    for (DateRange b : DateRange.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

