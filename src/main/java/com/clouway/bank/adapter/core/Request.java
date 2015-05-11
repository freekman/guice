package com.clouway.bank.adapter.core;

import java.util.Map;

/**
 * Created byivan.genchev1989@gmail.com.
 */
public class Request {
  private Map<String, String[]> map;

  public Request(Map<String, String[]> map) {
    this.map = map;
  }

  public String param(String name) {
    return map.get(name)[0];
  }
}
