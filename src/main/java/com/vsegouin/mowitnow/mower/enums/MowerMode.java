package com.vsegouin.mowitnow.mower.enums;

/**
 * Created by v.segouin on 07/09/2016.
 * This enum represents the differents mode possible for the mower
 * SAFE : Will avoid the outbound area and the mowers
 * UNSAFE : Will NOT avoid the outbound area and the mower
 * OPTIMIZED : Will not pass two times on the same mowed area, excepted if it can be avoided
 */
public enum MowerMode {
    SAFE,
    UNSAFE,
    OPTIMIZED
}
