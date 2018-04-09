package com.ttu.lunchbot.parser.menu;

/**
 * General exception class for menu parsers.
 * @see MenuParser
 */
public class MenuParserException extends RuntimeException {

    /**
     * Create a new exception.
     * @param message Message of the exception.
     */
    public MenuParserException(String message) {
        super(message);
    }
}
