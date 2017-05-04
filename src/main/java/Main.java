/**
 * Copyright (C) 2017 Bartek.
 */


import java.io.IOException;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import java.io.File;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;

/**
 * Main class.
 *
 * @version 1.0.0
 */
public class Main {
    public static void main(String[] args) {


      try {
        FileDataStore store = FileDataStoreFinder.getDataStore(new File("C:/Users/Bartek/Downloads/ne_110m_admin_0_countries/ne_110m_admin_0_countries.shp"));

        SimpleFeatureSource featureSource = store.getFeatureSource();

        String s = featureSource.getBounds().toString();

         // Create a map context and add our shapefile to it
        MapContext map = new DefaultMapContext();
        map.setTitle("Quickstart");
        map.addLayer(featureSource, null);
      } catch (IOException e) {
        e.printStackTrace();
      }




      final GeometryFactory gf = new GeometryFactory();

      final ArrayList<Coordinate> points = new ArrayList<Coordinate>();
      points.add(new Coordinate(-10, -10));
      points.add(new Coordinate(-10, 10));
      points.add(new Coordinate(10, 10));
      points.add(new Coordinate(10, -10));
      points.add(new Coordinate(-10, -10));
      final Polygon polygon = gf.createPolygon(new LinearRing(new CoordinateArraySequence(points
        .toArray(new Coordinate[points.size()])), gf), null);

      final Coordinate coord = new Coordinate(0, 0);
      final Point point = gf.createPoint(coord);

      System.out.println(point.within(polygon));


    }
}
