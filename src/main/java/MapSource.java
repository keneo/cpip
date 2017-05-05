import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Point;
import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.DataUtilities;
import org.geotools.data.collection.SpatialIndexFeatureCollection;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.Name;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Bartek on 05/05/2017.
 */
public class MapSource {

  public final SpatialIndexFeatureCollection countries;
  final static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

  public MapSource(String countryFile) throws IOException {
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
    this.countries = new SpatialIndexFeatureCollection(features);
  }

  public String countryInPoint(Point p) {
    Filter f = ff.contains(ff.property("the_geom"), ff.literal(p));
    SimpleFeatureCollection subFeatures = countries.subCollection(f);

    SimpleFeature singleOrNull = singleOrNull(subFeatures);

    return (singleOrNull==null)?"international":singleOrNull.getAttribute("name").toString();
  }

  private static SimpleFeature singleOrNull(SimpleFeatureCollection subFeatures) {
    if (subFeatures.isEmpty()) return null;
    assert subFeatures.size()==1;

    return subFeatures.features().next();
  }

  public Envelope boundingBox() {
    return this.countries.getBounds();
  }
}
