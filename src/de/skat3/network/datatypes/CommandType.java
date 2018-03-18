package de.skat3.network.datatypes;

public enum CommandType implements SubType{
    
    BID_REQUEST, BID_INFO, BID_REDO,
    PLAY_REQUEST, PLAY_INFO, PLAY_REDO,
    TRICK_INFO, ROUND_INFO, MATCH_INFO, GAME_INFO;
}
