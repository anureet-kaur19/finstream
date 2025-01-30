package com.finstream;

public class ReportingApplication {
  public static void main(String[] args) {
    System.out.println("Hello world!");
  }
}

// TODO:
// - handling 429 too many requests in optimal way
// - retry logic for kafka publishing
// - retry logic for finnhub API call failure
