package com.clouway.bank.adapter.core;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
public interface Bank {

  void withdraw(double value);

  void deposit( double value);

  double getBalance(String userName);

}
