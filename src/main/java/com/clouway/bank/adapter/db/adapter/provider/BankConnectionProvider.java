package com.clouway.bank.adapter.db.adapter.provider;

import com.clouway.bank.adapter.http.ConnectionFilter;

import java.sql.Connection;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class BankConnectionProvider implements Provider<Connection> {
  @Override
  public Connection get() {
    return ConnectionFilter.connections.get();
  }
}
