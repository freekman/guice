package com.clouway.bank.adapter.db.adapter.persistent;

import com.clouway.bank.adapter.core.Account;
import com.clouway.bank.adapter.core.AccountFinder;
import com.clouway.bank.adapter.core.AccountRegister;
import com.clouway.bank.adapter.core.MissingUserException;
import com.clouway.bank.adapter.db.adapter.datastore.DataStore;
import com.clouway.bank.adapter.db.adapter.datastore.RowFetcher;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class PersistentAccountRepository implements AccountRegister, AccountFinder {

  private DataStore dataStore;

  @Inject
  public PersistentAccountRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void register(Account account) {
    try {
      dataStore.update("insert into account (user_name,balance) values (?,?)", account.uName, 0.0);
    } catch (SQLException e) {
      throw new MissingUserException();
    }
  }

  @Override
  public void updateAccountBalance(String userName, double newBalance) {
    try {
      dataStore.update("update account set balance=? where user_name=?", newBalance, userName);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Account> findAll() {
    return dataStore.fetchRows("select * from account", new RowFetcher<Account>() {
      @Override
      public Account fetchRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("user_name");
        double balance = resultSet.getDouble("balance");
        return new Account(name, balance);
      }
    });
  }

  @Override
  public List<Account> findByUserName(String name) {
    return dataStore.fetchRows("select * from account where user_name='" + name + "'", new RowFetcher<Account>() {
      @Override
      public Account fetchRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("user_name");
        double balance = resultSet.getDouble("balance");
        return new Account(name, balance);
      }
    });
  }
}
