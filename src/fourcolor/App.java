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
package fourcolor;


import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author kfalconer
 */public class App {

    private HashMap<String, State> NamedStates;
    private Color[] colors;
    private ArrayList<ArrayList<String>> stateNeighbors;

    public App() {
        NamedStates = new HashMap<String, State>();
        stateNeighbors = new ArrayList<ArrayList<String>>();

        colors = new Color[4];
        colors[0] = Color.RED;
        colors[1] = Color.GREEN;
        colors[2] = Color.BLUE;
        colors[3] = Color.YELLOW;
    }

    public void getPlacemarks(String kmlFileName) {
        Kml kml =
                Kml.unmarshal(
                new File(kmlFileName),
                true);    // the second argument demands that the file be validated automatically during unmarshalling and checked if the object graph meets all contrainsts defined in OGX's KML schema specification
        Document root = (Document) kml.getFeature();
        ArrayList<Placemark> placemarks = (ArrayList) root.getFeature();
        int folderSize = placemarks.size();

        for (int i = 0; i < folderSize; i++) {
            Placemark thisState = (Placemark) placemarks.get(i);
            State temp = new State(thisState.getName());
            temp.setColor(colors[Integer.parseInt((int) ((Math.random() * 100) % 4) + "")]);
            temp.setPlacemark(thisState);
            temp.setBoundary(thisState.getGeometry());
            NamedStates.put(temp.getName(), temp);
        }
        getNeighborData();
        System.out.println(stateNeighbors.toString());

    }

    private void getNeighborData() {
        NeighborData n = new NeighborData();
        stateNeighbors = n.getNeighbors();
    }

    public ArrayList<String> checkColorConflict(String s) {
        ArrayList<String> result = new ArrayList<String>();

        Iterator neighborIterator = stateNeighbors.iterator();

        Color thisColor = NamedStates.get(s).getColor();

        while (neighborIterator.hasNext()) {
            ArrayList<String> tempNeighborList = (ArrayList<String>) neighborIterator.next();
            String tempNeighbor = tempNeighborList.get(0);
            if (tempNeighbor.equals(s)) {
                boolean skippedFirst = false;
                for (String st : tempNeighborList) {
                    if (thisColor.equals(NamedStates.get(st).getColor())) {
                        if (!skippedFirst){
                            
                            skippedFirst = true;
                        } else {
                            result.add(NamedStates.get(st).getName());
                        }
                    }
                }
            }
        }

        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    public String[] getStateList() {
        String[] result = new String[1];
        return NamedStates.keySet().toArray(result);
    }

    public void resetBorderColor(String s) {
        State selectedState = (State) NamedStates.get(s);
        selectedState.setBorderColor(selectedState.getColor());
    }

    public void setStateColor(String s, Color c) {
        State selectedState = (State) NamedStates.get(s);
        selectedState.setColor(c);
    }

    public Color getStateColor(String s) {
        return NamedStates.get(s).getColor();
    }

    public void setStateBorderColor(String s, Color c) {
        State selectedState = (State) NamedStates.get(s);
        selectedState.setBorderColor(c);
    }

    public Color[] getColors() {
        return colors;
    }

    public HashSet<Path2D> getStatePolygons(String s) {
        return NamedStates.get(s).toPolygons();
    }

    public Geometry[] getStateBoundaries() {
        ArrayList<Geometry> result = new ArrayList<Geometry>();
        Iterator mapContents = NamedStates.values().iterator();
        while (mapContents.hasNext()) {
            result.add(((State) mapContents.next()).getBoundary());
        }
        return (Geometry[]) result.toArray();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final App other = (App) obj;
        if (this.NamedStates != other.NamedStates && (this.NamedStates == null || !this.NamedStates.equals(other.NamedStates))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.NamedStates != null ? this.NamedStates.hashCode() : 0);
        return hash;
    }
}
