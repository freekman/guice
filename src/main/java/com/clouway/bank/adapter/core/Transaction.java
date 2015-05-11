package com.clouway.bank.adapter.core;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public interface Transaction {

  boolean isValid(String query, String value);
}
