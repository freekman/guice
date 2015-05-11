package com.clouway.bank.adapter.http;

import com.clouway.bank.adapter.core.*;
import com.clouway.bank.adapter.db.adapter.persistent.PersistentAccountRepository;
import com.clouway.bank.adapter.db.adapter.persistent.PersistentSessionRepository;
import com.clouway.bank.adapter.db.adapter.persistent.PersistentUserRepository;
import com.clouway.bank.adapter.db.adapter.provider.BankConnectionProvider;
import com.clouway.bank.adapter.db.adapter.provider.Provider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.google.sitebricks.SitebricksModule;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class GuiceCreator extends GuiceServletContextListener {
  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new ServletModule() {
      @Override
      protected void configureServlets() {
        bind(Bank.class).to(IBank.class);
        bind(Transaction.class).to(TransactionValidator.class);
        bind(SessionRegister.class).to(PersistentSessionRepository.class);
        bind(SessionFinder.class).to(PersistentSessionRepository.class);
        bind(AccountRegister.class).to(PersistentAccountRepository.class);
        bind(AccountFinder.class).to(PersistentAccountRepository.class);
        bind(Provider.class).to(BankConnectionProvider.class);
        bind(UserRegister.class).to(PersistentUserRepository.class);
        bind(UserFinder.class).to(PersistentUserRepository.class);
        bind(UserRegister.class).to(PersistentUserRepository.class);
        filter("/*").through(ConnectionFilter.class);
        filter("/home","/welcome").through(SecurityFilter.class);
      }
    }, new SitebricksModule() {
      @Override
      protected void configureSitebricks() {
        at("/home").show(HomePage.class);
        at("/register").show(RegistrationPage.class);
        at("/login").show(LoginPage.class);
        at("/welcome").show(WelcomePage.class);
        at("/logout").show(Logout.class);
        at("/manager").show(BankManager.class);
        at("/authenticator").show(UserAuthenticator.class);
      }
    });
  }
}
