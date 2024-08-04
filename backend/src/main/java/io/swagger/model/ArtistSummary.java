package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ArtistSummary
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-01-08T23:33:38.407Z[GMT]")

@Entity
public class ArtistSummary   {
  @JsonProperty("id")
  @Id
  private String id = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("numberOfsongs")
  private Integer numberOfsongs = null;

  @OneToOne(targetEntity=Song.class,
          fetch= FetchType.EAGER)
  @Valid
  @JsonProperty("earliestRelease")
  private Song earliestRelease = null;

  @OneToOne(targetEntity=Song.class,
          fetch= FetchType.EAGER)
  @Valid
  @JsonProperty("latestRelease")
  private Song latestRelease = null;

  @JsonProperty("highestPopularity")
  private Integer highestPopularity = null;

  public ArtistSummary id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(example = "2", required = true, accessMode = Schema.AccessMode.READ_ONLY, description = "")
      @NotNull

    public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public ArtistSummary name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(example = "The Allan Parson Project", required = true, description = "")
      @NotNull

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArtistSummary numberOfsongs(Integer numberOfsongs) {
    this.numberOfsongs = numberOfsongs;
    return this;
  }

  /**
   * Get numberOfsongs
   * @return numberOfsongs
   **/
  @Schema(description = "")
  
    public Integer getNumberOfsongs() {
    return numberOfsongs;
  }

  public void setNumberOfsongs(Integer numberOfsongs) {
    this.numberOfsongs = numberOfsongs;
  }

  public ArtistSummary earliestRelease(Song earliestRelease) {
    this.earliestRelease = earliestRelease;
    return this;
  }

  /**
   * Get earliestRelease
   * @return earliestRelease
   **/
  @Schema(description = "")
  
    @Valid
    public Song getEarliestRelease() {
    return earliestRelease;
  }

  public void setEarliestRelease(Song earliestRelease) {
    this.earliestRelease = earliestRelease;
  }

  public ArtistSummary latestRelease(Song latestRelease) {
    this.latestRelease = latestRelease;
    return this;
  }

  /**
   * Get latestRelease
   * @return latestRelease
   **/
  @Schema(description = "")
  
    @Valid
    public Song getLatestRelease() {
    return latestRelease;
  }

  public void setLatestRelease(Song latestRelease) {
    this.latestRelease = latestRelease;
  }

  public ArtistSummary highestPopularity(Integer highestPopularity) {
    this.highestPopularity = highestPopularity;
    return this;
  }

  /**
   * Get highestPopularity
   * @return highestPopularity
   **/
  @Schema(description = "")
  
    public Integer getHighestPopularity() {
    return highestPopularity;
  }

  public void setHighestPopularity(Integer highestPopularity) {
    this.highestPopularity = highestPopularity;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArtistSummary artistSummary = (ArtistSummary) o;
    return Objects.equals(this.id, artistSummary.id) &&
        Objects.equals(this.name, artistSummary.name) &&
        Objects.equals(this.numberOfsongs, artistSummary.numberOfsongs) &&
        Objects.equals(this.earliestRelease, artistSummary.earliestRelease) &&
        Objects.equals(this.latestRelease, artistSummary.latestRelease) &&
        Objects.equals(this.highestPopularity, artistSummary.highestPopularity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, numberOfsongs, earliestRelease, latestRelease, highestPopularity);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArtistSummary {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    numberOfsongs: ").append(toIndentedString(numberOfsongs)).append("\n");
    sb.append("    earliestRelease: ").append(toIndentedString(earliestRelease)).append("\n");
    sb.append("    latestRelease: ").append(toIndentedString(latestRelease)).append("\n");
    sb.append("    highestPopularity: ").append(toIndentedString(highestPopularity)).append("\n");
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
