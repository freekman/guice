package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.SessionRegister;
import com.clouway.bank.adapter.core.SidProvider;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Get;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/logout")
@Show("home.html")
public class Logout {
  private SessionRegister sessionRegister;
  private final Provider<HttpServletResponse> responseProvider;
  @Inject
  public Logout(SessionRegister sessionRegister,Provider<HttpServletResponse> responseProvider) {
    this.sessionRegister = sessionRegister;
    this.responseProvider = responseProvider;
  }
  @Get
  public HomePage logOut(HttpServletRequest req) {
    String sid = new SidProvider(req).get();
    if (sid != null) {
      sessionRegister.removeSessionIfExist(sid);
      Cookie currentCookie = new SidProvider(req).getCookie();
      currentCookie.setMaxAge(0);
      responseProvider.get().addCookie(currentCookie);
    }
    return new HomePage();
  }

}
