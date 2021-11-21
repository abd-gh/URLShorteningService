package com.stackfortech.urlShorteningService.service;

import com.stackfortech.urlShorteningService.model.Url;
import com.stackfortech.urlShorteningService.model.UrlProcessing;
import org.springframework.stereotype.Service;

@Service
public interface UrlService
{
    public Url generateShortUrl(UrlProcessing urlProc );
    public Url getEncodedUrl(String url);
    public Url persistShortUrl(Url url);
    
  
}
