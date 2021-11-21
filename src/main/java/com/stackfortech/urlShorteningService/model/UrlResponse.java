package com.stackfortech.urlShorteningService.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class UrlResponse
{
    @Autowired
    Environment environment;
    private String originalUrl;
    private String shortenedUrl;
    private LocalDateTime expirationDate;

    public UrlResponse(String originalUrl, String shortenedUrl, LocalDateTime expirationDate) {
        this.originalUrl = originalUrl;
        this.shortenedUrl = shortenedUrl;
        this.expirationDate = expirationDate;
    }

    public UrlResponse() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getshortenedUrl() {
        return shortenedUrl;
    }

    public void setshortenedUrl(String shortenedUrl) {
        this.shortenedUrl = "load/"+shortenedUrl;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "UrlResponseDto{" +
                "originalUrl='" + originalUrl + '\'' +
                ", shortCode="+ shortenedUrl + '\'' +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
