package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.Bank;
import com.clouway.bank.adapter.core.Transaction;
import com.google.inject.Inject;
import com.google.sitebricks.At;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

/**
 * Created byivan.genchev1989@gmail.com.
 */
@At("/manager")
@Show("welcome.html")
public class BankManager {
  private final Transaction transaction;
  private final Bank bank;
  public String uName = "";
  public Double balance = 0.0;
  public String operation;
  public String amount;


  @Inject
  public BankManager(Transaction transaction, Bank bank) {
    this.transaction = transaction;
    this.bank = bank;
  }

  @Post
  public String makeTransactionIfPossible() {
      boolean transactionIsValid = transaction.isValid(operation, amount);
      if (operation.equals("withdraw") && transactionIsValid) {
        bank.withdraw(getDoubleValue(amount));
      } else if (operation.equals("deposit") && transactionIsValid) {
        bank.deposit(getDoubleValue(amount));
      }
    return "/welcome";
  }

  private double getDoubleValue(String value) {
    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      return 0.0;
    }
  }
}
