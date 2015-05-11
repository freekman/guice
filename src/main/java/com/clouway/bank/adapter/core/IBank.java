package com.clouway.bank.adapter.core;

import com.clouway.bank.adapter.db.adapter.persistent.PersistentAccountRepository;
import com.clouway.bank.adapter.http.CurrentUserAccount;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

/**
 * @author Ivan Genchev (ivan.genchev1989@gmail.com)
 */
@Singleton
public class IBank implements Bank {

  private PersistentAccountRepository persistentAccountRepository;
  private CurrentUserAccount currentUserAccount;

  @Inject
  public IBank(PersistentAccountRepository persistentAccountRepository,CurrentUserAccount currentUserAccount) {
    this.persistentAccountRepository = persistentAccountRepository;
    this.currentUserAccount = currentUserAccount;
  }

  @Override
  public void withdraw( double value) {
    List<Account> accounts = persistentAccountRepository.findByUserName(currentUserAccount.get().uName);
    double currentBalance = accountCurrentBalance(accounts);
    if (currentBalance > value) {
      persistentAccountRepository.updateAccountBalance(currentUserAccount.get().uName, accountCurrentBalance(accounts) - value);
    }
  }

  @Override
  public void deposit( double value) {
    List<Account> accounts = persistentAccountRepository.findByUserName(currentUserAccount.get().uName);
    persistentAccountRepository.updateAccountBalance(currentUserAccount.get().uName, accountCurrentBalance(accounts) + value);
  }

  @Override
  public double getBalance(String userName) {
    List<Account> accounts = persistentAccountRepository.findByUserName(userName);
    return accountCurrentBalance(accounts);
  }

  private double accountCurrentBalance(List<Account> accounts) {
    if (accounts != null) {
      return accounts.get(0).balance;
    }
    return 0.00;
  }
}
