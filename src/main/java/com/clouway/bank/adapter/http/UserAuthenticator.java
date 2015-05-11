package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.SessionRegister;
import com.clouway.bank.adapter.core.SidProvider;
import com.clouway.bank.adapter.core.User;
import com.clouway.bank.adapter.core.UserFinder;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/authenticator")
@Show("login.html")
public class UserAuthenticator {

  private final UserFinder userFinder;
  private final SessionRegister sessionRegister;
  private final Provider<HttpServletRequest> requestProvider;
  private final Provider<HttpServletResponse> resp;

  @Inject
  public UserAuthenticator(UserFinder userFinder, SessionRegister sessionRegister, Provider<HttpServletRequest> requestProvider, Provider<HttpServletResponse> resp) {
    this.userFinder = userFinder;
    this.sessionRegister = sessionRegister;
    this.requestProvider = requestProvider;
    this.resp = resp;
  }

  public boolean authenticate(User user) {
    List<User> users = userFinder.findAll();
    if (users.contains(user)) {
      return true;
    }
    return false;
  }

  public void createAndSaveSession(User user) {
    String sid = new SidProvider(requestProvider.get()).get();
    if (sid == null) {
      UUID uuid = new UUID(10, 5);
      String randomValue = "vankaBanka" + uuid.randomUUID().toString();
      sid = sha1(randomValue);
      resp.get().addCookie(new Cookie("sid",sid));
    }
    sessionRegister.createSessionIfNotExist(sid, user.name, Calendar.getInstance().getTimeInMillis());
  }

  static String sha1(String input) {
    MessageDigest mDigest = null;
    try {
      mDigest = MessageDigest.getInstance("SHA1");
    } catch (NoSuchAlgorithmException e) {
      return "";
    }
    byte[] result = mDigest.digest(input.getBytes());
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < result.length; i++) {
      sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }
}
