/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fourcolor;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * Copyright 2012 Kyle Falconer
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author Kyle Falconer
 */
public class State {

    HashSet<Path2D> LandMass;
    HashSet<String> neighbors;
    Geometry boundary;
    Color color,BorderColor;
    String name;
    Placemark rawData;

    public State(String n) {
        name = n;
        boundary = new Polygon();
        color = new Color(125, 125, 125, 255);
        BorderColor = new Color(125, 125, 125, 255);
        rawData = null;
        LandMass = new HashSet<Path2D>();
        neighbors = new HashSet<String>();
    }
    

    public void setBoundary(Geometry b) {
        boundary = b;
    }
    public Geometry getBoundary() {
        return boundary;
    }
    
    public void setColor(Color c) {
        color = c;
        BorderColor = new Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }
    public Color getColor() {
        return color;
    }

    public void setBorderColor(Color c) {
        BorderColor = c;
    }
    public void resetBorderColor() {
        BorderColor = new Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha());
    }
    public Color getBorderColor() {
        return BorderColor;
    }

    public void setPlacemark(Placemark p) {
        rawData = p;
    }

    public String getName() {
        return name;
    }
    
    public HashSet<String> getNeighbors(){
        return neighbors;
    }

   

    /**
     * @return State name followed by color;
     */
    @Override
    public String toString() {
        return name + ": " + color;
    }

    public void Paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(2.0f));

        LinearRing bounds = (LinearRing) rawData.getGeometry();
        List<Coordinate> coordinates = bounds.getCoordinates();
        GeneralPath p = new GeneralPath(GeneralPath.WIND_NON_ZERO);
        boolean started = false;

        for (Coordinate c : coordinates) {
            if (!started) {
                p.moveTo(c.getLatitude(), c.getLongitude());
                started = true;

                continue;
            }

            p.lineTo(c.getLatitude(), c.getLongitude());
        }

        p.closePath();

        // translate origin towards center of canvas
        g2.translate(100.0f, 100.0f);

        // render the star's path
        g2.draw(p);
    }

    /**
     * In order to do this, we need to normalize the geographic coordinates to
     * coordinates which correspond to the coordinate space for graphical
     * representation. To do this, I adjust the Latitude and Longitude values by
     * a constant. The constant used should be somewhat close to the numeric
     * value of the geographical coordinate. For any single shape, I chose the
     * minimum values for each Long. and Lat. This normalized the left and
     * bottom bounds to zero on the carteisian plane. The values are then
     * amplified by a desired number, for instance, 1000, to shift the decimal
     * over far enough such that when it is normalized to an integer, it has a
     * distinct enough presence to be differentiated from the other points. For
     * now, the normalizing values will be: Lat: 38.78823394 Lon: -77.1223283
     *
     * @return
     */
    public HashSet<Path2D> toPolygons() {
        if (LandMass.isEmpty()) {
            MultiGeometry multigeometry = (MultiGeometry) rawData.getGeometry();

            geometryToPaths(multigeometry);
        }

        return LandMass;
    }

    private void geometryToPaths(Geometry geo) {
        System.out.println("Preparing to create a polygonal landmass for " + this.getName());

        ArrayList<Double> xCoords = new ArrayList<Double>();    // Lat
        ArrayList<Double> yCoords = new ArrayList<Double>();    // Lon

        if (geo instanceof Polygon) {
            Polygon polygon = (Polygon) geo;
            Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
            LinearRing linearRing = outerBoundaryIs.getLinearRing();
            List<Coordinate> coordinates = linearRing.getCoordinates();

            Path2D path = new Path2D.Double();
            boolean started = false;
            for (Coordinate c : coordinates) {
                if (!started) {
                    path.moveTo(c.getLatitude(), c.getLongitude());
                    started = true;
                    continue;
                }
                path.lineTo(c.getLatitude(), c.getLongitude());
            }

            LandMass.add(path);
        } else {
            MultiGeometry multigeo = (MultiGeometry) geo;

            for (int i = 0; i < multigeo.getGeometry().size(); i++) {
                geometryToPaths(multigeo.getGeometry().get(i));
            }
        }
    }



    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.rawData != other.rawData && (this.rawData == null || !this.rawData.equals(other.rawData))) {
            return false;
        }
        return true;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
