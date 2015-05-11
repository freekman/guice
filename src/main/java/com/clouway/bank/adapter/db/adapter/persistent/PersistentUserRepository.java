package com.clouway.bank.adapter.db.adapter.persistent;

import com.clouway.bank.adapter.core.User;
import com.clouway.bank.adapter.core.UserAlreadyExistsException;
import com.clouway.bank.adapter.core.UserFinder;
import com.clouway.bank.adapter.core.UserRegister;
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
public class PersistentUserRepository implements UserRegister, UserFinder {

  private DataStore dataStore;

  @Inject
  public PersistentUserRepository(DataStore dataStore) {
    this.dataStore = dataStore;
  }

  @Override
  public void register(User user) {
    try {
      dataStore.update("insert into users (user_name,user_password) values (?,?)", user.name, user.password);
    } catch (SQLException e) {
      throw new UserAlreadyExistsException();
    }
  }

  @Override
  public List<User> findAll() {
    return dataStore.fetchRows("select * from users", new RowFetcher<User>() {
      @Override
      public User fetchRow(ResultSet resultSet) throws SQLException {
        String name = resultSet.getString("user_name");
        String pwd = resultSet.getString("user_password");
        return new User(name, pwd);
      }
    });
  }
}
