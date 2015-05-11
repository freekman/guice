package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.Account;
import com.clouway.bank.adapter.core.AccountFinder;
import com.clouway.bank.adapter.core.Session;
import com.clouway.bank.adapter.core.SessionFinder;
import com.clouway.bank.adapter.core.SidProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */

public class CurrentUserAccount {

  private final AccountFinder accountFinder;
  private final SessionFinder sessionFinder;
  private Provider<HttpServletRequest> req;

  @Inject
  public CurrentUserAccount(AccountFinder accountFinder, SessionFinder sessionFinder,Provider<HttpServletRequest> req) {
    this.accountFinder = accountFinder;
    this.sessionFinder = sessionFinder;
    this.req = req;
  }

  public Account get(){
    List<Session> sessions;
    Account account = null;
    String sessionId = new SidProvider(req.get()).get();
    sessions = sessionFinder.findById(sessionId);
    if (sessions.size() != 0) {
      String userName = sessions.get(0).userName;
      List<Account> accounts = accountFinder.findByUserName(sessions.get(0).userName);
       account = accounts.get(0);
  }
    return account;
 }
}
