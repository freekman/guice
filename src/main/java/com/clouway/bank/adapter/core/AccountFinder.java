package com.clouway.bank.adapter.core;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface AccountFinder {

  List<Account> findAll();

  List<Account> findByUserName(String name);
}
