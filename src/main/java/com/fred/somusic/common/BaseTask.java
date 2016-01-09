package com.fred.somusic.common;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.springframework.cloud.CloudFactory;

public class BaseTask {

  private CouchDbInstance getDb() {
    CouchDbInstance couchDb = new CloudFactory().getCloud().getServiceConnector("somusic-cloudant", CouchDbInstance.class,
        null);
    return couchDb;
  }

  protected CouchDbConnector getSongDb() {
    String dbname = "somusic-songs";
    // creates a database with the specified name
    CouchDbConnector dbc = getDb().createConnector(dbname, true);
    return dbc;
  }

  protected CouchDbConnector getStatsDb() {
    String dbname = "somusic-stats";
    // creates a database with the specified name
    CouchDbConnector dbc = getDb().createConnector(dbname, true);
    return dbc;
  }

}
