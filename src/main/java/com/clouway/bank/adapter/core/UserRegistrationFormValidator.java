package com.clouway.bank.adapter.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class UserRegistrationFormValidator {
  public List<String> validate(Request request) {
    List<String> errorList = new ArrayList<>();

    if (!request.param("uName").trim().matches("[a-zA-z0-9]{2,20}")) {
      errorList.add("User name must be from 2-20 chars or numbers.");
    }
    if (!request.param("pwd").trim().matches("\\^S|[a-zA-z0-9@!.?-]{2,20}")) {
      errorList.add("User password must be from 2-20 chars.");
    }
    return errorList;
  }
}
