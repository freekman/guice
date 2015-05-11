package com.clouway.bank.adapter.db.adapter.datastore;


import com.clouway.bank.adapter.db.adapter.provider.Provider;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
@Singleton
public class DataStore {

  private Provider<Connection> provider;

  @Inject
  public DataStore(Provider provider) {
    this.provider = provider;
  }

  /**
   * This method is using prepared statements.
   *
   * @param query-the  query to be executed
   * @param params-the needed value in the query can be from 0 to as many as needed.
   */

  public void update(String query, Object... params) throws SQLException {
    Connection conn = provider.get();

    PreparedStatement preparedStatement = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
    fillPreparedStatement(preparedStatement, params);
    preparedStatement.execute();

    preparedStatement.close();

  }

  /**
   * @param query
   * @param rowFetcher
   * @param <T>
   * @returns a List<T>
   */
  public <T> List<T> fetchRows(String query, RowFetcher<T> rowFetcher) {
    Connection conn = provider.get();
    List<T> result = Lists.newArrayList();
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(query);
      while (rs.next()) {
        T rowItem = rowFetcher.fetchRow(rs);
        result.add(rowItem);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return result;
  }

  private void fillPreparedStatement(PreparedStatement preparedStatement, Object[] params) {
    for (int i = 0; i < params.length; i++) {
      try {
        preparedStatement.setObject(i + 1, params[i]);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
