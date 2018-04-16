package de.skat3.gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public class BiddingValues {

  public static int[] values = fillBiddingValues();
  
  
  private static int[] fillBiddingValues() {
    ArrayList<Integer> temp = new ArrayList<Integer>();
    temp.add(23);
    temp.add(35);
    temp.add(46);
    temp.add(59);
    for (int i = 9; i <= 12; i++) {
      for (int j = 2; j <= 18; j++) {
        temp.add(i * j);
      }
    }
    for (int i = 2; i <= 11; i++) {
      temp.add(i * 24);
    }
    Collections.sort(temp);
    int[] values = new int[temp.size()];
    for (int i = 0; i < temp.size(); i++) {
      values[i] = temp.get(i);
    }
    return values;
  }
}