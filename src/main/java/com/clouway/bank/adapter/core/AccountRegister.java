package com.clouway.bank.adapter.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface AccountRegister {

  void register(Account account);

  void updateAccountBalance(String userName, double newBalance);
}
