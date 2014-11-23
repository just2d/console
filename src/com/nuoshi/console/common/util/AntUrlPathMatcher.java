package com.nuoshi.console.common.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;


public class AntUrlPathMatcher {

    private boolean requiresLowerCaseUrl = true;
    private PathMatcher pathMatcher = new AntPathMatcher();

    public AntUrlPathMatcher() {
        this(true);
    }

    public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    public Object compile(String path) {
        if (requiresLowerCaseUrl) {
            return path.toLowerCase();
        }

        return path;
    }

    public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    public boolean pathMatchesUrl(Object path, String url) {
        return pathMatcher.match((String)path, url);
    }

    public String getUniversalMatchPattern() {
        return "/**";
    }

    public boolean requiresLowerCaseUrl() {
        return requiresLowerCaseUrl;
    }

    public String toString() {
        return getClass().getName() + "[requiresLowerCase='" + requiresLowerCaseUrl + "']";
    }

public static void main(String[] args) {
	PathMatcher pathMatcher = new AntPathMatcher();
	System.out.println(pathMatcher.match("/a/*/kk/*", "/a/add/kk/s"));
}
}
