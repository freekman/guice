package com.clouway.bank.adapter.db.adapter.persistent;

import com.clouway.bank.adapter.core.Session;
import com.clouway.bank.adapter.core.SessionFinder;
import com.clouway.bank.adapter.core.SessionRegister;
import com.clouway.bank.adapter.db.adapter.datastore.DataStore;
import com.clouway.bank.adapter.db.adapter.datastore.RowFetcher;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class PersistentSessionRepository implements SessionRegister, SessionFinder {

  private final DataStore dataStore;

  @Inject
  public PersistentSessionRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }


  @Override
  public void createSessionIfNotExist(String sessionID, String userName, Long timeOut) {
    try {
      dataStore.update("insert into session (session_id,user_name,session_timecreated) values(?,?,?)", sessionID, userName, timeOut);
    } catch (SQLException e) {
      System.out.println("Sesslon already exists");
    }
  }

  @Override
  public void removeSessionIfExist(String sessionID) {
    try {
      dataStore.update("delete from session where session_id=?", sessionID);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public boolean refreshSession(String sessionId, long newTime) {
    Session session = getUserSessionById(sessionId);
    if (session == null) {
      return false;
    }
    if (isValid(session, 1)) {
      try {
        dataStore.update("update session set session_timecreated=? where session_id=?", newTime, sessionId);
      } catch (SQLException e) {
        return false;
      }
      return true;
    }
    return false;
  }

  @Override
  public List<Session> findAll() {
    return dataStore.fetchRows("select * from session", new RowFetcher<Session>() {
      @Override
      public Session fetchRow(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("session_id");
        long timeCreated = resultSet.getLong("session_timecreated");
        String userName = resultSet.getString("user_name");
        return new Session(id, userName, timeCreated);
      }
    });
  }

  @Override
  public List<Session> findById(String id) {
    return dataStore.fetchRows("select * from session where session_id='" + id + "'", new RowFetcher<Session>() {
      @Override
      public Session fetchRow(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("session_id");
        long timeCreated = resultSet.getLong("session_timecreated");
        String userName = resultSet.getString("user_name");
        return new Session(id, userName, timeCreated);
      }
    });
  }

  private Session getUserSessionById(String id) {
    if (findById(id).isEmpty()) {
      return null;
    }
    return findById(id).get(0);
  }

  private boolean isValid(Session userSession, int lifeTimeInMinutes) {
    long lifeTimeInMilliseconds = lifeTimeInMinutes * 60000;
    if (Calendar.getInstance().getTimeInMillis() - userSession.sessionTimeCreated > lifeTimeInMilliseconds) {
      removeSessionIfExist(userSession.sessionId);
      return false;
    }
    return true;
  }
}
