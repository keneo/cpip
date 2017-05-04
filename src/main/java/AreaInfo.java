import com.vividsolutions.jts.geom.Envelope;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.filter.Filter;
import org.opengis.filter.FilterFactory2;

public class AreaInfo {


  public final SimpleFeature entireInOneCountry;
  public final SimpleFeatureCollection allCountriesInArea;


  public AreaInfo(FilterFactory2 ff, SimpleFeatureCollection parentAreaAllCountries, Envelope env) {
    Filter filterCountryIntersectsEnvelope = ff.intersects(ff.property("the_geom"), ff.literal(env));

    SimpleFeature entireInOneCountryCandidate = null;

    this.allCountriesInArea = parentAreaAllCountries.subCollection(filterCountryIntersectsEnvelope);
    if (this.allCountriesInArea.size()==1) {
      Filter filterCountryContainsEnvelope = ff.contains(ff.property("the_geom"), ff.literal(env));
      SimpleFeatureCollection countriesContainingOurEnvelope = parentAreaAllCountries.subCollection(filterCountryContainsEnvelope);
      if (countriesContainingOurEnvelope.size()==1) {
        entireInOneCountryCandidate = countriesContainingOurEnvelope.features().next();
      } else {
        assert countriesContainingOurEnvelope.size()==0;
      }

    }

    this.entireInOneCountry = entireInOneCountryCandidate;
  }
}
