package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.User;
import com.clouway.bank.adapter.core.UserFactory;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/login")
@Show("login.html")
public class LoginPage {
  public String uName = "";
  public String pwd = "";
  private UserAuthenticator userAuthenticator;
  @Inject
  private Injector injector;

  @Inject
  public LoginPage(UserAuthenticator userAuthenticator) {
    this.userAuthenticator = userAuthenticator;
  }
  @Post
  public Object login() {
    User user = new UserFactory().create(uName, pwd);
    if (userAuthenticator.authenticate(user)) {
      userAuthenticator.createAndSaveSession(user);
      return injector.getInstance(WelcomePage.class);
    }
    return new HomePage();
  }
}
