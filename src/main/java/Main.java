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
        CountryLocatorQuadTree locator = new CountryLocatorQuadTree(source);
        Random rnd = new Random(5);

        int todoCount = 1000000;
        log("Running "+todoCount+" random geo lookups...");

        for (int i=0; i<1000000; i++) {
          double x = rnd.nextDouble() * 360-180;
          double y = rnd.nextDouble() * 180-90;

          //x=0.0;
          //y=51.0;

          String country = locator.getCountryAt(x, y);

          if (i<10)
            log("At point: "+x+","+y+": "+country);
          else if (i==10)
            log("...");
        }

        log("Finished");

      } catch (IOException e) {
        e.printStackTrace();
      }

    }

    private static void log(String line) {
      System.out.println(new Date().toString() + " " +line);
    }
}
