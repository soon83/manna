package com.sss.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public class UriGenerator {

    public static URI getLocation(Long id) throws URISyntaxException {
        String path = (id == null) ? "" : "/" + id;
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return new URI(requestUri.substring(requestUri.indexOf("/", 8)) + path);
    }

    public static URI getLocation(String token) throws URISyntaxException {
        String path = (token == null) ? "" : "/" + token;
        String requestUri = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        return new URI(requestUri.substring(requestUri.indexOf("/", 8)) + path);
    }
}