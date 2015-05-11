package com.clouway.bank.adapter.validators;

import com.clouway.bank.adapter.core.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class UserFormValidator  {
  private List<String> list = new ArrayList<>();


  public List<String> validate(User user) {
    if (!user.name.matches("[a-zA-z0-9]{2,20}")) {
      list.add("The name size must be from 2-20 only letters and numbers!");
    }
    if (!user.password.matches("\\^S|[a-zA-z0-9@!.?-]{2,20}")) {
      list.add("The password size must be from 2-20,without any white space!");
    }
    return list;
  }
}
