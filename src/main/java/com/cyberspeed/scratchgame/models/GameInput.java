package com.cyberspeed.scratchgame.models;

/**
 * Record that contains the location of the configuration file and user betting amount.
 *
 * @param configFile    location of the game configuration JSON file
 * @param bettingAmount user betting amount
 */
public record GameInput(String configFile, int bettingAmount) {

}
