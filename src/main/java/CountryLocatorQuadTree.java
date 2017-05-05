import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.filter.FilterFactory2;

/**
 * Created by Bartek on 05/05/2017.
 */
public class CountryLocatorQuadTree implements ICountryLocator {

  private final Node rootNode;
  private final MapSource source;

  public CountryLocatorQuadTree(MapSource source) {
    this.source = source;
    this.rootNode = new Node(source.countries, source.boundingBox());
  }

  public String getCountryAt(double x, double y) {
    //String classicLookupResult = source.countryInPoint(gf.createPoint(new Coordinate(x,y)));
    String treeLookupResult = this.rootNode.countryInPoint(x,y);

    //assert classicLookupResult == treeLookupResult;

    return treeLookupResult;
    //return "most possibly russia";
  }
}
