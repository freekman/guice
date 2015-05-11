package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.Account;
import com.clouway.bank.adapter.core.AccountFinder;
import com.clouway.bank.adapter.core.Session;
import com.clouway.bank.adapter.core.SessionFinder;
import com.clouway.bank.adapter.core.SidProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;
import com.google.sitebricks.http.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/welcome")
@Show("welcome.html")
public class WelcomePage {

  private final SessionFinder sessionFinder;
  private final AccountFinder accountFinder;
  private final Provider<HttpServletRequest> req;
  public String uName;
  public Double balance = 0.0;

  @Inject
  public WelcomePage(SessionFinder sessionFinder, AccountFinder accountFinder, Provider<HttpServletRequest> req) {
    this.sessionFinder = sessionFinder;
    this.accountFinder = accountFinder;
    this.req = req;
  }
  @Get
  @Post
  public Object setInfo() {
    String sid = new SidProvider(req.get()).get();
    List<Session> sessions = sessionFinder.findById(sid);
    Session current = null;
    if (sessions != null && sessions.size() != 0) {
      current = sessions.get(0);
    }
    if (current != null) {
      List<Account> accounts = accountFinder.findByUserName(current.userName);
      uName = current.userName;
      balance = accounts.get(0).balance;
    }
    return null;
  }
}
