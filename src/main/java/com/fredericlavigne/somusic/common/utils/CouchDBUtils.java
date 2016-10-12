package com.fredericlavigne.somusic.common.utils;

import java.util.logging.Logger;

import org.ektorp.CouchDbConnector;
import org.ektorp.DbAccessException;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.DesignDocument;
import org.ektorp.support.DesignDocument.View;

public class CouchDBUtils {

  public final static Logger LOGGER = Logger.getLogger(CouchDBUtils.class.getName());

  public static void createDesignView(CouchDbConnector db, String designDocumentName, String viewName,
      String viewDefinition) {
    String designDocumentId = "_design/" + designDocumentName;
    DesignDocument dd;
    try {
      dd = db.get(DesignDocument.class, designDocumentId);
      LOGGER.info("Design document with id " + designDocumentId + " already exists");
    } catch (DocumentNotFoundException e) {
      LOGGER.info("Design document with id " + designDocumentId + " does not exists. Creating it.");
      dd = new DesignDocument(designDocumentId);
      db.create(dd);
    }

    if (dd.containsView(viewName)) {
      LOGGER.info("View with id " + viewName + " already exists");
    } else {
      LOGGER.info("View with id " + viewName + " does not exists. Creating it.");
      dd.addView(viewName, new View(viewDefinition));
      db.update(dd);
    }
  }

  public static void doWithinLimits(Runnable runnable) {
    boolean limitCaught = false;
    do {
      try {
        limitCaught = false;
        runnable.run();
      } catch (DbAccessException dbex) {
        if (dbex.getLocalizedMessage().contains("429")) {
          limitCaught = true;
          LOGGER.info("Being rate limited by Cloudant, let's pause a bit before retrying");
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
          }
        }
      }
    } while (limitCaught);
  }

}
