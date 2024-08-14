package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

    private Map<String, String> longToShortUrlMap = new HashMap<>();
    private Map<String, Integer> hitCountMap = new HashMap<>();
    private Map<String, String> shortToLongUrlMap = new HashMap<>();

    @Override
    public String registerNewUrl(String longUrl) {
        return registerNewUrl(longUrl, generateShortUrl());
    }

    @Override
    public String registerNewUrl(String longUrl, String shortUrl) {
        if (shortToLongUrlMap.containsKey(shortUrl)) {
            return null; // shortUrl already exists
        }

        longToShortUrlMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        String longUrl = shortToLongUrlMap.get(shortUrl);
        if (longUrl != null) {
            hitCountMap.put(longUrl, hitCountMap.getOrDefault(longUrl, 0) + 1);
        }
        return longUrl;
    }

    @Override
    public Integer getHitCount(String longUrl) {
        return hitCountMap.getOrDefault(longUrl, 0);
    }

    @Override
    public String delete(String longUrl) {
        String shortUrl = longToShortUrlMap.remove(longUrl);
        if (shortUrl != null) {
            shortToLongUrlMap.remove(shortUrl);
        }
        return shortUrl;
    }

    private String generateShortUrl() {
        return "http://short.url/" + generateRandomAlphanumericString();
    }

    private String generateRandomAlphanumericString() {
        
        int length = 9;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
