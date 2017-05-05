import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by Bartek on 05/05/2017.
 */
public class CountryLocatorQuadTreeTest {
  @Test
  public void testHelloWorld() throws Exception {
    String str = "Junit is working fine";
    assertEquals("Junit is working fine",str);
  }

  @Test
  public void getCountryAt() throws Exception {

    MapSource source = new MapSource("build/ne_110m_admin_0_countries.shp");  // this file is now downloaded as part of the "gradle run"
    ICountryLocator locatorTree = new CountryLocatorQuadTree(source);
    ICountryLocator locatorClassic = new CountryLocatorClassic(source);
    Random rnd = new Random(5);

    for (int i=0; i<100; i++) {
      double x = rnd.nextDouble() * 360-180;
      double y = rnd.nextDouble() * 180-90;

      String expected = locatorClassic.getCountryAt(x, y);
      String actual = locatorTree.getCountryAt(x, y);

      assertEquals("tree based locator should return same value as the classic-adhoc locator. Run number: "+i,expected,actual);
    }

  }

}
