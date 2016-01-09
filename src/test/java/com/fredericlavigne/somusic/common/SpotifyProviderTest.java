package com.fredericlavigne.somusic.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fredericlavigne.somusic.common.providers.Provider;
import com.fredericlavigne.somusic.common.providers.SpotifyProvider;
import com.fredericlavigne.somusic.common.providers.Track;

public class SpotifyProviderTest {

  @Test
  public void test() throws Exception {    
    Provider provider = new SpotifyProvider();    
    Track track = (Track) provider.find("https://open.spotify.com/track/2gUy7VMkIbPSP92dkZ9iLa");
    assertEquals("Avantasia", track.getArtist());
    assertEquals("https://open.spotify.com/track/2gUy7VMkIbPSP92dkZ9iLa", provider.find(track));

    track = (Track) provider.find("https://open.spotify.com/album/3ZsVFaemow9pcZTTfVu3pZ/6tRsQGQ0oocjdtTuc1N3Mb");
    assertEquals("HAVE AN ILLUSION", track.getName());

  }

}
