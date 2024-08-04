package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Song
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-01-08T23:33:38.407Z[GMT]")
@Entity
@Table(name = "tracks")
public class Song   {
  @JsonProperty("title")
  private String title = null;

  @JsonProperty("id")
  @Id
  private String id = null;

  @JsonProperty("popularity")
  private Integer popularity = null;

  @JsonProperty("duration_ms")
  private Integer durationMs = null;

  @JsonProperty("explicit")
  private Boolean explicit = null;

  @JsonProperty("artists")
  private String artists = null;

  @JsonProperty("id_artists")
  private String idArtists = null;

  @JsonProperty("release_date")
  private String releaseDate = null;

  public Song title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @Schema(example = "Eye in the sky", required = true, description = "")
      @NotNull

    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Song id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "1", required = true, accessMode = Schema.AccessMode.READ_ONLY, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Song popularity(Integer popularity) {
    this.popularity = popularity;
    return this;
  }

  /**
   * Get popularity
   * @return popularity
   **/
  @Schema(example = "30", description = "")
  
    public Integer getPopularity() {
    return popularity;
  }

  public void setPopularity(Integer popularity) {
    this.popularity = popularity;
  }

  public Song durationMs(Integer durationMs) {
    this.durationMs = durationMs;
    return this;
  }

  /**
   * Get durationMs
   * @return durationMs
   **/
  @Schema(example = "63500", description = "")
  
    public Integer getDurationMs() {
    return durationMs;
  }

  public void setDurationMs(Integer durationMs) {
    this.durationMs = durationMs;
  }

  public Song explicit(Boolean explicit) {
    this.explicit = explicit;
    return this;
  }

  /**
   * Get explicit
   * @return explicit
   **/
  @Schema(example = "false", description = "")
  
    public Boolean isExplicit() {
    return explicit;
  }

  public void setExplicit(Boolean explicit) {
    this.explicit = explicit;
  }

  public Song artists(String artists) {
    this.artists = artists;
    return this;
  }

  /**
   * Get artists
   * @return artists
   **/
  @Schema(description = "")
  
    public String getArtists() {
    return artists;
  }

  public void setArtists(String artists) {
    this.artists = artists;
  }

  public Song idArtists(String idArtists) {
    this.idArtists = idArtists;
    return this;
  }

  /**
   * Get idArtists
   * @return idArtists
   **/
  @Schema(description = "")
  
    public String getIdArtists() {
    return idArtists;
  }

  public void setIdArtists(String idArtists) {
    this.idArtists = idArtists;
  }

  public Song releaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  /**
   * Get releaseDate
   * @return releaseDate
   **/
  @Schema(description = "")
  
    public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Song song = (Song) o;
    return Objects.equals(this.title, song.title) &&
        Objects.equals(this.id, song.id) &&
        Objects.equals(this.popularity, song.popularity) &&
        Objects.equals(this.durationMs, song.durationMs) &&
        Objects.equals(this.explicit, song.explicit) &&
        Objects.equals(this.artists, song.artists) &&
        Objects.equals(this.idArtists, song.idArtists) &&
        Objects.equals(this.releaseDate, song.releaseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, id, popularity, durationMs, explicit, artists, idArtists, releaseDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Song {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    popularity: ").append(toIndentedString(popularity)).append("\n");
    sb.append("    durationMs: ").append(toIndentedString(durationMs)).append("\n");
    sb.append("    explicit: ").append(toIndentedString(explicit)).append("\n");
    sb.append("    artists: ").append(toIndentedString(artists)).append("\n");
    sb.append("    idArtists: ").append(toIndentedString(idArtists)).append("\n");
    sb.append("    releaseDate: ").append(toIndentedString(releaseDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
