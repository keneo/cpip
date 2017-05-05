import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.filter.FilterFactory2;

/**
 * Created by Bartek on 05/05/2017.
 */
public class Node {

  private final AreaInfo areaInfo;
  private final Envelope envelope;
  private double _midX;
  private double _midY;
  private final static GeometryFactory gf = new GeometryFactory();
  private final static FilterFactory2 ff = CommonFactoryFinder.getFilterFactory2();

  public Node(SimpleFeatureCollection parentCountries, Envelope envelope) {
    this.envelope = envelope;
    this._midX = (envelope.getMinX()+envelope.getMaxX())/2 ;
    this._midY = (envelope.getMinY()+envelope.getMaxY())/2 ;
    this.areaInfo = new AreaInfo(ff,parentCountries,envelope, gf);
  }

  public String countryInPoint(double x, double y) {
    if (this.areaInfo.entireAreaOneCountryOrNull!=null) {
      return this.areaInfo.entireAreaOneCountryOrNull;
    } else {
      return findCountryInSubNodes(x,y);
    }
  }

  private String findCountryInSubNodes(double x, double y) {
    Node subnode = this.getSubnode(x,y);
    return subnode.countryInPoint(x,y);
  }

  private Node[] _subNodes;
  private Node getSubnode(double x, double y) {
    if (_subNodes ==null) _subNodes =new Node[4];

    boolean isLeftHalf = isLeftHalf(x);
    boolean isBottomHalf = isBottomHalf(y);
    int subnodeIndex = calculateSubnodeIndex(isLeftHalf, isBottomHalf);

    Node subnode = _subNodes[subnodeIndex];
    if (subnode ==null){
      Envelope subNodeEnv = createEnvelopeForSubnode(isLeftHalf, isBottomHalf);
      subnode=new Node(this.areaInfo.allCountriesInArea,subNodeEnv);
      _subNodes[subnodeIndex]= subnode;
    }
    return subnode;
  }

  private Envelope createEnvelopeForSubnode(boolean isLeftHalf, boolean isBottomHalf) {
    double x1;
    double x2;
    double y1;
    double y2;
    if (isLeftHalf)
    {
      x1 = this.envelope.getMinX();
      x2 = this._midX;
    } else {
      x1 = this._midX;
      x2 = this.envelope.getMaxX();
    }
    if (isBottomHalf)
    {
      y1 = this.envelope.getMinY();
      y2 = this._midY;
    } else {
      y1 = this._midY;
      y2 = this.envelope.getMaxY();
    }
    return new Envelope(x1,x2,y1,y2);
  }

  private int calculateSubnodeIndex(boolean isLeftHalf, boolean isBottomHalf) {
    return (isLeftHalf?0:1) + (isBottomHalf?0:2);
  }

  private boolean isBottomHalf(double y) {
    return y<=_midY;
  }

  private boolean isLeftHalf(double x) {
    return x<=_midX;
  }
}
