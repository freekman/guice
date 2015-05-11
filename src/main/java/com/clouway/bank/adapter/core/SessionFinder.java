package com.clouway.bank.adapter.core;

import java.util.List;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public interface SessionFinder {
  List<Session> findAll();

  List<Session> findById(String id);

}
