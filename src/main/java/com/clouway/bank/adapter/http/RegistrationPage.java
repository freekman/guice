package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.Account;
import com.clouway.bank.adapter.core.AccountRegister;
import com.clouway.bank.adapter.core.User;
import com.clouway.bank.adapter.core.UserAlreadyExistsException;
import com.clouway.bank.adapter.core.UserRegister;
import com.clouway.bank.adapter.validators.UserFormValidator;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/register")
@Show("register.html")
public class RegistrationPage {

  public String uName = "";
  public String pwd = "";
  private List<String> errors;
  private UserRegister userRegistry;
  private AccountRegister accountRegister;

  public List<String> getErrors() {
    return errors;
  }

  @Inject
  public RegistrationPage(UserRegister userRegistry,AccountRegister accountRegister) {

    this.userRegistry = userRegistry;
    this.accountRegister = accountRegister;
  }

  @Post
  public void get() {
    System.out.println(uName + " , " + pwd);
    User user = new User(uName, pwd);
    errors = new UserFormValidator().validate(user);

    if (errors.size() == 0) {
      errors = new ArrayList<String>() {{
        add("Registration successful.");
      }};
      try {
        userRegistry.register(user);
        accountRegister.register(new Account(uName, 0.0));
      }catch (UserAlreadyExistsException e){
        errors = new ArrayList<String>() {{
          add("User already exists.");
        }};
      }
    }
  }

}
