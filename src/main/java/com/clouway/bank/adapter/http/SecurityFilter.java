package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.Session;
import com.clouway.bank.adapter.core.SessionFinder;
import com.clouway.bank.adapter.core.SessionRegister;
import com.clouway.bank.adapter.core.SidProvider;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class SecurityFilter implements Filter {

  private SessionFinder sessionFinder;
  private SessionRegister sessionRegister;

  @Inject
  public SecurityFilter(SessionFinder sessionFinder, SessionRegister sessionRegister) {
    this.sessionFinder = sessionFinder;
    this.sessionRegister = sessionRegister;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    boolean isActive = false;
    String reqURI = ((HttpServletRequest) request).getRequestURI();
    String destination = getDestination(reqURI);
    String sessionId = new SidProvider((HttpServletRequest) request).get();
    List<Session> sessions = sessionFinder.findAll();
    Session currentSession = null;
    for (Session current : sessions) {
      if (current.sessionId.equals(sessionId)) {
        currentSession = current;
      }
    }
    if (currentSession != null) {
      isActive = sessionRegister.refreshSession(currentSession.sessionId, Calendar.getInstance().getTimeInMillis());
    }
    if (!isActive && destination.equalsIgnoreCase("home")) {
      chain.doFilter(request, response);
      return;
    }
    if (isActive && destination.equalsIgnoreCase("welcome")) {
      chain.doFilter(request, response);
      return;
    }
    if (!isActive && (destination.equalsIgnoreCase("home") || destination.equalsIgnoreCase("welcome") || destination.equalsIgnoreCase("manager"))) {
      ((HttpServletResponse) response).sendRedirect("/home");
      return;

    } else if (isActive && (destination.equalsIgnoreCase("home") || destination.equalsIgnoreCase("welcome") || destination.equalsIgnoreCase("register") || destination.equalsIgnoreCase("registration"))) {
      ((HttpServletResponse) response).sendRedirect("/welcome");
      return;
    }
  }
  @Override
  public void destroy() {

  }
  private String getDestination(String reqURI) {
    String[] dest = reqURI.split("/");
    return dest[dest.length - 1];
  }
}
