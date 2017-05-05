import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.filter.FilterFactory2;

/**
 * Created by Bartek on 05/05/2017.
 */
public class CountryLocator {

  private final Node rootNode;
  private final MapSource source;
  private final GeometryFactory gf = new GeometryFactory();
  final static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

  public CountryLocator(MapSource source) {
    this.source = source;
    this.rootNode = new Node(source.countries, source.boundingBox(), ff, gf);
  }

  public String getCountryAt(double x, double y) {
    //String classicLookupResult = source.countryInPoint(gf.createPoint(new Coordinate(x,y)));
    String treeLookupResult = this.rootNode.countryInPoint(x,y);

    //assert classicLookupResult == treeLookupResult;

    return treeLookupResult;
    //return "most possibly russia";
  }
}
