package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets SongFilterKeys
 */
public enum SongFilterKeys {
  TITLE("title"),
    ID_ARTISTS("id_artists"),
    POPULARITY("popularity"),
    RELEASE_DATE("release_date");

  private String value;

  SongFilterKeys(String value) {
    this.value = value;
  }

  @Override
  @JsonValue
  public String toString() {
    return String.valueOf(value);
  }

  @JsonCreator
  public static SongFilterKeys fromValue(String text) {
    for (SongFilterKeys b : SongFilterKeys.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
}
