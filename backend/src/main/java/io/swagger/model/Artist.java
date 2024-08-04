package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Song;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Artist
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2023-01-08T23:33:38.407Z[GMT]")
@Entity
@Table(name="artists")
public class Artist   {
  @Id
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("followers")
  private Integer followers = null;

  @JsonProperty("genres")
  private String genres = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("popularity")
  private Integer popularity = null;

  @OneToMany(targetEntity=Song.class, mappedBy="id",
          fetch= FetchType.EAGER)
  @JsonProperty("songs")
  @Valid
  private List<Song> songs = null;

  public Artist id(String id) {
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

  public Artist followers(Integer followers) {
    this.followers = followers;
    return this;
  }

  /**
   * Get followers
   * @return followers
   **/
  @Schema(example = "990000", description = "")
  
    public Integer getFollowers() {
    return followers;
  }

  public void setFollowers(Integer followers) {
    this.followers = followers;
  }

  public Artist genres(String genres) {
    this.genres = genres;
    return this;
  }

  /**
   * Get genres
   * @return genres
   **/
  @Schema(description = "")
  
    public String getGenres() {
    return genres;
  }

  public void setGenres(String genres) {
    this.genres = genres;
  }

  public Artist name(String name) {
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

  public Artist popularity(Integer popularity) {
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

  public Artist songs(List<Song> songs) {
    this.songs = songs;
    return this;
  }

  public Artist addSongsItem(Song songsItem) {
    if (this.songs == null) {
      this.songs = new ArrayList<Song>();
    }
    this.songs.add(songsItem);
    return this;
  }

  /**
   * Get songs
   * @return songs
   **/
  @Schema(description = "")
      @Valid
    public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Artist artist = (Artist) o;
    return Objects.equals(this.id, artist.id) &&
        Objects.equals(this.followers, artist.followers) &&
        Objects.equals(this.genres, artist.genres) &&
        Objects.equals(this.name, artist.name) &&
        Objects.equals(this.popularity, artist.popularity) &&
        Objects.equals(this.songs, artist.songs);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, followers, genres, name, popularity, songs);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Artist {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    followers: ").append(toIndentedString(followers)).append("\n");
    sb.append("    genres: ").append(toIndentedString(genres)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    popularity: ").append(toIndentedString(popularity)).append("\n");
    sb.append("    songs: ").append(toIndentedString(songs)).append("\n");
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
