import java.io.IOException;
import java.util.Random;

/**
 * Copyright (C) 2017 Bartek.
 */



public class Main {
    public static void main(String[] args) {

      try {

        MapSource source = new MapSource("C:/Users/Bartek/Downloads/ne_110m_admin_0_countries/ne_110m_admin_0_countries.shp");
        CountryLocator locator = new CountryLocator(source);
        Random rnd = new Random(8);

        for (int i=0; i<1000000; i++) {
          double x = rnd.nextDouble() * 180-90;
          double y = rnd.nextDouble() * 360-180;

          //x=0.0;
          //y=51.0;

          System.out.print("At point: "+x+","+y+": ");

          String country = locator.getCountryAt(x, y);

          System.out.println(country);
        }

      } catch (IOException e) {
        e.printStackTrace();
      }

    }
}
