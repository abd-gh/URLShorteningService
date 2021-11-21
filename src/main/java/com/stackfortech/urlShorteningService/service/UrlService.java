package com.stackfortech.urlShorteningService.service;

import com.stackfortech.urlShorteningService.model.Url;
import com.stackfortech.urlShorteningService.model.UrlProcessing;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

@Service
public interface UrlService
{
    public Url generateShortUrl(UrlProcessing urlProc ) throws ValidationException;
    public Url getEncodedUrl(String url);
    public Url persistShortUrl(Url url);
  
}
