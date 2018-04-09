package com.ttu.lunchbot.util;

/**
 * Wrapper and utility tool for working with the Facebook Graph API.
 */
public class FacebookGraphUtility {

    /**
     * Base URL of the Facebook Graph API.
     */
    private static final String BASE_URL = "https://graph.facebook.com/";

    /**
     * Application key and secret as one access token.
     */
    private final String token;

    /**
     * Create and initialize the utility with an access token.
     * @param token Application key and secret as one access token.
     */
    public FacebookGraphUtility(String token) {
        this.token = token;
    }

    /**
     * @return Query string segment containing the access token.
     */
    private String getAccessTokenQueryString() {
        return "access_token=" + this.token;
    }

    /**
     * @param pageId Facebook page id from which posts are to be fetched
     * @return Facebook Graph API URL for fetching most recent posts of a Facebook page
     */
    public String getPostsUrl(String pageId) {
        return BASE_URL + pageId + "/posts" + "?" + this.getAccessTokenQueryString();
    }
}
