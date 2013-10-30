/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fourcolor;

import java.util.ArrayList;


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
class NeighborData {

    ArrayList<ArrayList<String>> stateNeighbors;

    NeighborData() {
        stateNeighbors = new ArrayList<ArrayList<String>>();
        setup();
    }

    public ArrayList<ArrayList<String>> getNeighbors() {
        return stateNeighbors;
    }

    private void setup() {
        //    stateNeighbors.put("null, null)


        int[][] states = {
            {1,
                9, 10, 23, 41
            }, {2,
                2, 5, 27, 30, 43
            }, {3,
                17, 23, 24, 35, 41, 42
            }, {4,
                2, 27, 36
            }, {5,
                2, 15, 26, 30, 35, 43, 49
            }, {6,
                20, 31, 38
            }, {7,
                19, 29, 37
            }, {8,
                19, 45
            }, {9,
                1, 10
            }, {10,
                1, 9, 32, 39, 41
            }, {11,
                25, 27, 36, 43, 46, 49
            }, {12,
                13, 14, 16, 24, 48
            }, {13,
                12, 16, 21, 34
            }, {14,
                12, 22, 24, 26, 40, 48
            }, {15,
                5, 24, 26, 35
            }, {16,
                12, 13, 24, 34, 41, 45, 47
            }, {17,
                2, 23, 42
            }, {18,
                28
            }, {19,
                7, 8, 37, 45, 47
            }, {20,
                6, 28, 31, 38, 44
            }, {21,
                13, 34, 48
            }, {22,
                14, 33, 40, 48
            }, {23,
                13, 17, 41
            }, {24,
                3, 12, 14, 15, 16, 26, 35, 41
            }, {25,
                11, 33, 40, 49
            }, {26,
                5, 14, 15, 24, 40, 49
            }, {27,
                2, 4, 11, 36, 43
            }, {28,
                18, 20, 44
            }, {29,
                7, 31, 37
            }, {30,
                2, 5, 35, 42, 43
            }, {31,
                6, 20, 29, 37, 44
            }, {32,
                10, 39, 41, 45
            }, {33,
                22, 25, 40
            }, {34,
                13, 16, 21, 37, 47
            }, {35,
                3, 5, 15, 24, 30, 42
            }, {36,
                4, 11, 27, 46
            }, {37,
                7, 19, 29, 31, 34, 47
            }, {38,
                6, 20
            }, {39,
                10, 32
            }, {40,
                14, 22, 25, 26, 33, 49
            }, {41,
                1, 3, 10, 16, 23, 24, 32, 45
            }, {42,
                3, 17, 30, 35
            }, {43,
                2, 5, 11, 27, 30, 49
            }, {44,
                20, 28, 31
            }, {45,
                8, 16, 19, 32, 41, 47
            }, {46,
                11, 36
            }, {47,
                16, 19, 34, 37, 45
            }, {48,
                12, 14, 21, 22
            }, {49,
                5, 11, 25, 26, 40, 43
            }
        };

        String[] stateNames = {
            "Alabama",
            "Arizona",
            "Arkansas",
            "California",
            "Colorado",
            "Connecticut",
            "Delaware",
            "District of Columbia",
            "Florida",
            "Georgia",
            "Idaho",
            "Illinois",
            "Indiana",
            "Iowa",
            "Kansas",
            "Kentucky",
            "Louisiana",
            "Maine",
            "Maryland",
            "Massachusetts",
            "Michigan",
            "Minnesota",
            "Mississippi",
            "Missouri",
            "Montana",
            "Nebraska",
            "Nevada",
            "New Hampshire",
            "New Jersey",
            "New Mexico",
            "New York",
            "North Carolina",
            "North Dakota",
            "Ohio",
            "Oklahoma",
            "Oregon",
            "Pennsylvania",
            "Rhode Island",
            "South Carolina",
            "South Dakota",
            "Tennessee",
            "Texas",
            "Utah",
            "Vermont",
            "Virginia",
            "Washington",
            "West Virginia",
            "Wisconsin",
            "Wyoming",};

   
  

        ArrayList<ArrayList<String>> tempSet = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < stateNames.length; i++) {

            tempSet.add(new ArrayList<String>());
            tempSet.get(i).add(stateNames[i]);
            for (int j = 1; j < states[i].length; j++) {
                for (int k = 0; k < stateNames.length; k++) {
                    if (states[i][j] == k) {
                        tempSet.get(i).add(stateNames[k - 1]);
                    }
                }
            }
        }
        stateNeighbors = tempSet;
    }
}
