package com.example.animalchipization;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

public class PolygonTest {

    @Test
    public void polygon() {
        GeometryFactory factory = new GeometryFactory();

        Coordinate a1 = new Coordinate(1, 1);
        Coordinate a2 = new Coordinate(4, 1);
        Coordinate a3 = new Coordinate(4, 4);
        Coordinate a4 = new Coordinate(1, 4);
        Coordinate a5 = new Coordinate(1, 1);
        Polygon a = factory.createPolygon(new Coordinate[]{a1, a2, a3, a4, a5});

        Coordinate b1 = new Coordinate(4, 4);
        Coordinate b2 = new Coordinate(6, 4);
        Coordinate b3 = new Coordinate(6, 6);
        Coordinate b4 = new Coordinate(4, 6);
        Coordinate b5 = new Coordinate(4, 4);
        Polygon b = factory.createPolygon(new Coordinate[]{b1, b2, b3, b4, b5});

        Coordinate c1 = new Coordinate(4, 2);
        Coordinate c2 = new Coordinate(5, 2);
        Coordinate c3 = new Coordinate(5, 3);
        Coordinate c4 = new Coordinate(4, 3);
        Coordinate c5 = new Coordinate(4, 2);
        Polygon c = factory.createPolygon(new Coordinate[]{c1, c2, c3, c4, c5});

        Coordinate d1 = new Coordinate(0, 3);
        Coordinate d2 = new Coordinate(2, 3);
        Coordinate d3 = new Coordinate(2, 5);
        Coordinate d4 = new Coordinate(0, 5);
        Coordinate d5 = new Coordinate(0, 3);
        Polygon d = factory.createPolygon(new Coordinate[]{d1, d2, d3, d4, d5});

        Coordinate e1 = new Coordinate(2, 2);
        Coordinate e2 = new Coordinate(3, 2);
        Coordinate e3 = new Coordinate(3, 3);
        Coordinate e4 = new Coordinate(2, 3);
        Coordinate e5 = new Coordinate(2, 2);
        Polygon e = factory.createPolygon(new Coordinate[]{e1, e2, e3, e4, e5});

        Coordinate f1 = new Coordinate(2, 1);
        Coordinate f2 = new Coordinate(3, 1);
        Coordinate f3 = new Coordinate(3, 2);
        Coordinate f4 = new Coordinate(2, 2);
        Coordinate f5 = new Coordinate(2, 1);
        Polygon f = factory.createPolygon(new Coordinate[]{f1, f2, f3, f4, f5});

        Coordinate s1 = new Coordinate(10, 10);
        Coordinate s2 = new Coordinate(15, 10);
        Coordinate s3 = new Coordinate(15, 15);
        Coordinate s4 = new Coordinate(10, 15);
        Coordinate s5 = new Coordinate(10, 10);
        Polygon s = factory.createPolygon(new Coordinate[]{s1, s2, s3, s4, s5});

        boolean ab = a.touches(b);
        boolean ac = a.touches(c);
        boolean ad = a.touches(d);
        boolean ae = a.touches(e);
        boolean af = a.touches(f);
        boolean as = a.touches(s);


    }
}
