package com.fred.somusic.common.model;

import java.util.Date;

import org.ektorp.support.CouchDbDocument;

import com.google.gson.annotations.Expose;

public class Song extends CouchDbDocument {

  private static final long serialVersionUID = 1L;

  public String sourceId;

  @Expose
  public String link;

  @Expose
  public String artist;

  @Expose
  public String album;
  
  @Expose
  public String title;

  @Expose
  public String image;
  
  public static enum Status {
    NEW, DATA_FOUND, IMAGE_FOUND, STATS_UPDATED, FAILED
  };

  public Status state = Status.NEW;

  public Date createdAt;

  public String getSourceId() {
    return sourceId;
  }
  
  public void setSourceId(String sourceId) {
    this.sourceId = sourceId;
  }
  
  public Status getState() {
    return state;
  }
    
  public void setState(Status state) {
    this.state = state;
  }
  
  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }
  
  public void setAlbum(String album) {
    this.album = album;
  }
  
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }
  
  public void setImage(String image) {
    this.image = image;
  }
  
  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "Song [link=" + link + ", artist=" + artist + ", title=" + title
        + ", createdAt=" + createdAt + ", state=" + state + "]";
  }

}
