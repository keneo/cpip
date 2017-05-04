// ripped from
// https://gis.stackexchange.com/questions/182447/reverse-geocode-latitude-longitude-with-geotools-using-natural-earth-shapefiles
// https://gitlab.com/snippets/15776



import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

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

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

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
    countries = new SpatialIndexFeatureCollection(ds.getFeatureSource(name).getFeatures());
  }

  public SimpleFeatureCollection lookup(Point p) {
    Filter f = ff.contains(ff.property("the_geom"), ff.literal(p));
    return countries.subCollection(f);

  }

  // simple test
  public static void main(String[] args) throws IOException {
    GeometryFactory gf = new GeometryFactory();

    SimpleGeoCoder geocoder = new SimpleGeoCoder();

    Point london = gf.createPoint(new Coordinate(0.0, 51.0));

    SimpleFeatureCollection features = geocoder.lookup(london);
    SimpleFeatureIterator itr = features.features();
    try {
      while (itr.hasNext()) {
        SimpleFeature f = itr.next();
        System.out.println(f.getAttribute("NAME"));
      }
    } finally {
      itr.close();
    }
  }
}
