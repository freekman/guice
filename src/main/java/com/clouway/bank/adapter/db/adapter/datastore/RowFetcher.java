package com.clouway.bank.adapter.db.adapter.datastore;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public interface RowFetcher<T> {
  T fetchRow(ResultSet resultSet) throws SQLException;
}
