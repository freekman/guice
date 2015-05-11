package com.clouway.bank.adapter.core;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Account {

  public final String uName;
  public final double balance;

  public Account(String uName, double balance) {
    this.uName = uName;
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Account account = (Account) o;

    if (Double.compare(account.balance, balance) != 0) return false;
    if (uName != null ? !uName.equals(account.uName) : account.uName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = uName != null ? uName.hashCode() : 0;
    temp = Double.doubleToLongBits(balance);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }
}
