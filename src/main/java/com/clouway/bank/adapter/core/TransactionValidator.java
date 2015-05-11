package com.clouway.bank.adapter.core;

import com.google.inject.Singleton;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
@Singleton
public class TransactionValidator implements Transaction {

  @Override
  public boolean isValid(String query, String value) {
    try {
      Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return false;
    }
    if (query.equals("deposit") && Double.parseDouble(value) < 0) {
      return false;
    }
    if (query.equals("withdraw") && Double.parseDouble(value) < 0) {
      return false;
    }
    return true;
  }
}
