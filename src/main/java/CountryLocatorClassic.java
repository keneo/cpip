import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Created by Bartek on 05/05/2017.
 */
public class CountryLocatorClassic implements ICountryLocator {
  private final MapSource source;
  private final GeometryFactory gf = new GeometryFactory();

  public CountryLocatorClassic(MapSource source) {
    this.source = source;
  }

  public String getCountryAt(double x, double y) {
    String classicLookupResult = source.countryInPoint(gf.createPoint(new Coordinate(x,y)));
    return classicLookupResult;
  }
}
