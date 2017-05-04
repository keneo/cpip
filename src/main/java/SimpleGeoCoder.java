// ripped from
// https://gis.stackexchange.com/questions/182447/reverse-geocode-latitude-longitude-with-geotools-using-natural-earth-shapefiles
// https://gitlab.com/snippets/15776



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.collection.SpatialIndexFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.Name;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;


public class SimpleGeoCoder {
  SpatialIndexFeatureCollection countries;
  final static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();
  final static public String countryFile = "C:/Users/Bartek/Downloads/ne_110m_admin_0_countries/ne_110m_admin_0_countries.shp";

  public SimpleGeoCoder() throws IOException {
    // load the country shapefile
    URL countryURL = DataUtilities.fileToURL(new File(countryFile));
    HashMap<String, Object> params = new HashMap<>();
    params.put("url", countryURL);
    DataStore ds = DataStoreFinder.getDataStore(params);
    if (ds == null) {
      throw new IOException("couldn't open " + params.get("url"));
    }
    Name name = ds.getNames().get(0);
    SimpleFeatureCollection features = ds.getFeatureSource(name).getFeatures();
    countries = new SpatialIndexFeatureCollection(features);
  }

  public SimpleFeature countryWithPoint(Point p) {
    Filter f = ff.contains(ff.property("the_geom"), ff.literal(p));
    SimpleFeatureCollection subFeatures = countries.subCollection(f);
    return singleOrNull(subFeatures);
  }

  private SimpleFeature singleOrNull(SimpleFeatureCollection subFeatures) {
    if (subFeatures.isEmpty()) return null;
    assert subFeatures.size()==1;

    return subFeatures.features().next();
  }

  public SimpleFeature countryWithPolygon(Polygon p) {
    Filter f = ff.contains(ff.property("the_geom"), ff.literal(p));
    SimpleFeatureCollection subFeatures = countries.subCollection(f);
    return singleOrNull(subFeatures);
  }

  public SimpleFeatureCollection allCountriesInPolygon(Polygon p) {
    Filter f = ff.intersects(ff.property("the_geom"), ff.literal(p));
    SimpleFeatureCollection subFeatures = countries.subCollection(f);
    return subFeatures;
  }


//  public SimpleFeatureCollection analyseSubbox(object parentBox, object subBox) {
//    Filter f = ff.contains(ff.property("the_geom"), ff.literal(p));
//    SimpleFeatureCollection subFeatures = countries.subCollection(f);
//    return subFeatures;
//  }

  // simple test
  public static void main(String[] args) throws IOException {
    GeometryFactory gf = new GeometryFactory();

    SimpleGeoCoder geocoder = new SimpleGeoCoder();

    Point london = gf.createPoint(new Coordinate(0.0, 51.0));

    final Polygon boxInLondon = createRectPolygonAround(gf, london, 0.0001);
    final Polygon boxWithLondon = createRectPolygonAround(gf, london, 1.0);
    final Polygon boxWithLondon2 = createRectPolygonAround(gf, london, 40.0);

    long startTime = System.currentTimeMillis();

    for (int i=0; i<1; i++) {
      printFeature("countryWithPoint(london):", geocoder.countryWithPoint(london));
      printFeature("countryWithPolygon(boxInLondon):", geocoder.countryWithPolygon(boxInLondon));
      printFeature("countryWithPolygon(boxWithLondon):", geocoder.countryWithPolygon(boxWithLondon));

      printFeatures("allCountriesInPolygon(boxInLondon):", geocoder.allCountriesInPolygon(boxInLondon));
      printFeatures("allCountriesInPolygon(boxWithLondon):", geocoder.allCountriesInPolygon(boxWithLondon));
      printFeatures("allCountriesInPolygon(boxWithLondon2):", geocoder.allCountriesInPolygon(boxWithLondon2));


    }

    long stopTime = System.currentTimeMillis();
    long elapsedTime = stopTime - startTime;
    System.out.println(elapsedTime);
  }

  private static void printFeatures(String title, SimpleFeatureCollection features) {
    System.out.print(title);
    System.out.print(": ");
    SimpleFeatureIterator itr = features.features();
    try {
      while (itr.hasNext()) {
        SimpleFeature f = itr.next();
        System.out.print(f.getAttribute("name"));
        System.out.print(", ");
      }
    } finally {
      itr.close();
    }
    System.out.println();  }

  private static void printFeature(String title, SimpleFeature feature) {
    System.out.print(title+": ");
    System.out.println(feature==null?"none":feature.getAttribute("name"));
  }

  private static Polygon createRectPolygonAround(GeometryFactory gf, Point london, double dist) {
    final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
    points.add(new Coordinate(london.getX() -dist, london.getY()-dist));
    points.add(new Coordinate(london.getX() -dist, london.getY()+dist));
    points.add(new Coordinate(london.getX() +dist, london.getY()+dist));
    points.add(new Coordinate(london.getX() +dist, london.getY()-dist));
    points.add(new Coordinate(london.getX() -dist, london.getY()-dist));
    return gf.createPolygon(new LinearRing(new CoordinateArraySequence(points.toArray(new Coordinate[points.size()])), gf), null);
  }
}
