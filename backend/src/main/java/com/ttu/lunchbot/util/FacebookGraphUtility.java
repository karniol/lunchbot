package com.ttu.lunchbot.util;

public class FacebookGraphUtility {

    private static final String BASE_URL = "https://graph.facebook.com/";

    private final String token;

    public FacebookGraphUtility(String token) {
        this.token = token;
    }

    private String getAccessTokenQueryString() {
        return "access_token=" + this.token;
    }

    public String getPostsUrl(String pageId) {
        return BASE_URL + pageId + "/posts" + "?" + this.getAccessTokenQueryString();
    }
}
