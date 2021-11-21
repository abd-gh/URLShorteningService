package com.stackfortech.urlShorteningService.Controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackfortech.urlShorteningService.domin.Error;
import com.stackfortech.urlShorteningService.domin.ErrorResponse;
import com.stackfortech.urlShorteningService.model.Url;
import com.stackfortech.urlShorteningService.model.UrlProcessing;
import com.stackfortech.urlShorteningService.model.UrlResponse;
import com.stackfortech.urlShorteningService.service.UrlService;

@RestController
public class UrlShorteningController
{
    @Autowired
    private UrlService urlService;
    
    @PostMapping("/shortening")
    public ResponseEntity<?> shorteningUrl(@RequestBody UrlProcessing url ){
		Url shortenedUrl = urlService.generateShortUrl(url);
		ResponseEntity responseEntity = null;
		if (shortenedUrl != null) {
			UrlResponse urlResp = new UrlResponse();
			urlResp.setOriginalUrl(shortenedUrl.getOriginalUrl());
			urlResp.setExpirationDate(shortenedUrl.getExpirationDate());
			urlResp.setshortenedUrl(shortenedUrl.getshortenedUrl());
			 responseEntity = new ResponseEntity<UrlResponse>(urlResp, HttpStatus.OK);
		} else {
			 Error error = new Error();
             error.withCode(1001).withMessage("There was an error processing your request. please try again.");
             responseEntity = new ErrorResponse().generateErrorResponse(error,HttpStatus.BAD_REQUEST);
		}
		return responseEntity;
    }
    


	@GetMapping("load/{shortenedUrl}")
	@ResponseBody
	public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortenedUrl, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		ResponseEntity responseEntity = null;
		Url urlLoad = urlService.getEncodedUrl(shortenedUrl);
		
		if (StringUtils.isEmpty(shortenedUrl)) {
			Error error = new Error();
			error.withCode(443).withMessage("Invalid Url");
			return responseEntity = new ErrorResponse().generateErrorResponse(error, HttpStatus.BAD_REQUEST);
		}
		else if(urlLoad == null)
        {
            Error error = new Error();
			error.withCode(443).withMessage("Url does not exist ");
			return responseEntity = new ErrorResponse().generateErrorResponse(error, HttpStatus.BAD_REQUEST);
        }

		else if (urlLoad.getExpirationDate().isBefore(LocalDateTime.now())) {
			Error error = new Error();
			error.withCode(408).withMessage("Request Timeout response Url has expired.");
			return responseEntity = new ErrorResponse().generateErrorResponse(error, HttpStatus.GATEWAY_TIMEOUT);
		}
		else {
		response.sendRedirect(urlLoad.getOriginalUrl());
		return null;
		}
	}
   
}
