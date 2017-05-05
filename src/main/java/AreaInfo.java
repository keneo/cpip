import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.GeometryAttributeImpl;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import java.util.ArrayList;

public class AreaInfo {

  public final String entireAreaOneCountryOrNull;
  public final SimpleFeatureCollection allCountriesInArea;


  public AreaInfo(FilterFactory2 ff, SimpleFeatureCollection parentAreaAllCountries, Envelope env, GeometryFactory gf) {
    Polygon poly = envelopeToPoly(env, gf);

    this.allCountriesInArea = findAllCountriesInArea(ff, parentAreaAllCountries, poly);
    this.entireAreaOneCountryOrNull = wholeInOneCountryOrNull(ff, parentAreaAllCountries, poly, this.allCountriesInArea);
  }

  private Polygon envelopeToPoly(Envelope env, GeometryFactory gf) {
    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
    points.add(new Coordinate(env.getMinX(), env.getMinY()));
    points.add(new Coordinate(env.getMinX(), env.getMaxY()));
    points.add(new Coordinate(env.getMaxX(), env.getMaxY()));
    points.add(new Coordinate(env.getMaxX(), env.getMinY()));
    points.add(new Coordinate(env.getMinX(), env.getMinY()));
    return gf.createPolygon(new LinearRing(new CoordinateArraySequence(points.toArray(new Coordinate[points.size()])), gf), null);
  }

  private String wholeInOneCountryOrNull(FilterFactory2 ff, SimpleFeatureCollection parentAreaAllCountries, Polygon poly, SimpleFeatureCollection allCountriesInOurArea) {
    if (allCountriesInOurArea.size()==1) {
//      final SimpleFeature theSingleCountryInOurArea = allCountriesInOurArea.features().next();
//      GeometryAttributeImpl o = (GeometryAttributeImpl)theSingleCountryInOurArea.getProperty("the_geom");

      final Filter filterCountryContainsEnvelope = ff.contains(ff.property("the_geom"), ff.literal(poly));
      final SimpleFeatureCollection countriesContainingOurEnvelope = allCountriesInOurArea.subCollection(filterCountryContainsEnvelope); //hottest call
      if (countriesContainingOurEnvelope.size()==1) {
        return countriesContainingOurEnvelope.features().next().getAttribute("name").toString();
      } else {
        assert countriesContainingOurEnvelope.size()==0;
      }
    } else if (allCountriesInOurArea.size()==0) {
      return "international";
    }
    return null;
  }

  private SimpleFeatureCollection findAllCountriesInArea(FilterFactory2 ff, SimpleFeatureCollection parentAreaAllCountries, Polygon poly) {
    final Filter filterCountryIntersectsEnvelope = ff.intersects(ff.property("the_geom"), ff.literal(poly));

    return parentAreaAllCountries.subCollection(filterCountryIntersectsEnvelope);
  }
}
