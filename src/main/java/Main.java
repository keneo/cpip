import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Copyright (C) 2017 Bartek.
 */



public class Main {
    public static void main(String[] args) {

      try {

        MapSource source = new MapSource("C:/Users/Bartek/Downloads/ne_110m_admin_0_countries/ne_110m_admin_0_countries.shp");
        CountryLocator locator = new CountryLocator(source);
        Random rnd = new Random(5);

        for (int i=0; i<1000000; i++) {
          double x = rnd.nextDouble() * 360-180;
          double y = rnd.nextDouble() * 180-90;

          //x=0.0;
          //y=51.0;

          log("At point: "+x+","+y+": ");

          String country = locator.getCountryAt(x, y);

          log(country);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }



    }

  private static void log(String line) {
    System.out.println(new Date().toString() + " " +line);
  }
}
