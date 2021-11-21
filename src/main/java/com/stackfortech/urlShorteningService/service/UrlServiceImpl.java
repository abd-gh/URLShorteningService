package com.stackfortech.urlShorteningService.service;

import com.google.common.hash.Hashing;
import com.stackfortech.urlShorteningService.model.Url;
import com.stackfortech.urlShorteningService.model.UrlProcessing;
import com.stackfortech.urlShorteningService.repository.UrlRepository;
import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
//import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.ValidationException;

@Component
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlRepository urlRepository;

	@Override
	public Url generateShortUrl(UrlProcessing urlReceived) throws ValidationException {

		if (StringUtils.isEmpty(urlReceived.getUrl())) {
			throw new ValidationException("The url cannot be null or empty, please check again");
		}
		String encodedUrl = encodeUrl(urlReceived.getUrl());
		Url url = new Url();
		url.setCreationDate(LocalDateTime.now());
		url.setOriginalUrl(urlReceived.getUrl());
		url.setshortenedUrl(encodedUrl);
		url.setExpirationDate(getExpirationDate(urlReceived.getExpirationDate(), url.getCreationDate()));
		Url urlToReturn = persistShortUrl(url);

		if (urlToReturn != null) {
			return urlToReturn;
		} else {
			throw new ValidationException("The url is not valid, please check again");
		}
	}
    
    private String encodeUrl(String url){
		String encodedUrl = "";
		LocalDateTime time = LocalDateTime.now();
		encodedUrl = Hashing.murmur3_32().hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		return encodedUrl;
	}

    private LocalDateTime getExpirationDate(String expirationDate, LocalDateTime creationDate){
        LocalDateTime expirationDateReturn = LocalDateTime.parse(expirationDate);
        return expirationDateReturn;
    }

    @Override
    public Url persistShortUrl(Url url) {
        Url urlReturn = urlRepository.save(url);
        return urlReturn;
    }

    @Override
    public Url getEncodedUrl(String urlsh) {
        Url EncodedUrlReturn = urlRepository.findByshortenedUrl(urlsh);
        return EncodedUrlReturn;
    }

}
