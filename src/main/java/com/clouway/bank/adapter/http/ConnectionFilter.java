package com.clouway.bank.adapter.http;

import com.google.inject.Singleton;
import org.postgresql.ds.PGPoolingDataSource;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
@Singleton
public class ConnectionFilter implements Filter {
  public static ThreadLocal<Connection> connections = new ThreadLocal<>();
  private PGPoolingDataSource source;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    source = new PGPoolingDataSource();
    source.setServerName("localhost");
    source.setDatabaseName("bank");
    source.setUser("postgres");
    source.setPassword("1234");
    source.setMaxConnections(10);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (connections.get() == null) {
      Connection conn = getConnection();
      connections.set(conn);
    }
    System.out.println(connections.get().toString());
    chain.doFilter(request, response);
    Connection conn = connections.get();
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    connections.set(null);
  }
  @Override
  public void destroy() {
  }
  private Connection getConnection() {
    try {
      return source.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}
