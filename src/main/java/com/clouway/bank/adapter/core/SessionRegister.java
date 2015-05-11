package com.clouway.bank.adapter.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionRegister {

  void createSessionIfNotExist(String sessionID, String userName, Long timeOut);

  void removeSessionIfExist(String sessionID);

  boolean refreshSession(String sessionId, long newTime);

}
