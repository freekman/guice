package com.clouway.bank.adapter.core;

import com.google.inject.Singleton;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@Singleton
public class UserFactory {
  public User create(String uName, String pwd) {
    return new User(uName, pwd);
  }
}
