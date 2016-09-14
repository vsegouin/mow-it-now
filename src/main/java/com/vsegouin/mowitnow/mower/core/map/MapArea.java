package com.vsegouin.mowitnow.mower.core.map;

import com.vsegouin.mowitnow.services.dependencies.util.InjectorUtil;
import com.vsegouin.mowitnow.services.language.interfaces.LanguageManagerInterface;
import org.apache.log4j.Logger;

/**
 * Created by v.segouin on 07/09/2016.
 * This class represents the area's map to be mowed
 */
public class MapArea {
    private static final Logger LOGGER = Logger.getLogger(MapArea.class);
    private final LanguageManagerInterface languageManager;
    private int maxOrdinate;
    private int maxAbscissa;

    private int[][] positionMatrix;

    /**
     * The basic constructor, will create a matrix corresponding to the max abscissa and ordinate given.
     *
     * @param abscissa the width of the matrix
     * @param ordinate the height of the matrix
     */
    public MapArea(final int abscissa, final int ordinate) {
        this.maxAbscissa = abscissa + 1;
        this.maxOrdinate = ordinate + 1;
        languageManager = InjectorUtil.getInjector().getInstance(LanguageManagerInterface.class);
        LOGGER.debug(languageManager.getString("creating-matrix", Integer.toString(maxAbscissa), Integer.toString(maxOrdinate)));
        positionMatrix = new int[maxAbscissa][maxOrdinate];
        for (int a = 0; a < maxAbscissa; a++) {
            for (int o = 0; o < maxOrdinate; o++) {
                positionMatrix[a][o] = 0;
            }
        }
    }

    /**
     * return the value at the x,y coordinate.
     *
     * @param a the abscissa to fetch
     * @param o the ordinate to fetch
     * @return the value at the asked coordinate
     */
    public int getCoordinate(final int a, final int o) {
        return this.positionMatrix[a][o];
    }

    /**
     * Set the value at the x,y coordinate.
     *
     * @param a     the abscissa to set
     * @param o     the ordinate to set
     * @param value the value to set
     */
    public void setCoordinate(final int a, final int o, final int value) {
        this.positionMatrix[a][o] = value;
    }

    /**
     * Return a representation of the map edited by the detectors of the mowers.
     *
     * @return a matrix representation of the map
     */
    public String getRepresentation() {
        StringBuilder representation = new StringBuilder();
        representation.append("\n\r");
        for (int ordinate = maxOrdinate - 1; ordinate >= 0; ordinate--) {
            representation.append("(");
            for (int abscissa = 0; abscissa <= maxAbscissa - 1; abscissa++) {
                representation.append(positionMatrix[abscissa][ordinate]).append(",");
            }
            representation.deleteCharAt(representation.length() - 1);
            representation.append(")\n\r");
        }
        return representation.toString();
    }

    public int[][] getMatrix() {
        return positionMatrix.clone();
    }

    public int getMaxOrdinate() {
        return maxOrdinate;
    }

    public int getMaxAbscissa() {
        return maxAbscissa;
    }

}
