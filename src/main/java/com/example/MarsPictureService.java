package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.AbstractMap;
import java.util.Map;

import static com.example.NasaApiClient.Picture;


@Service
@RequiredArgsConstructor
public class MarsPictureService {

    private final NasaApiClient nasaApiClient;
    private final RestTemplate restTemplate;

    public byte[] findLargestMarsPicture(int sol, String camera, String nasaApiKey) {
        return nasaApiClient.findAllPictures(sol, camera, nasaApiKey)
                .pictures()
                .parallelStream()
                .map(this::getPictureFinalUriAndSize)
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .map(this::getPictureBytes)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pictures found"));
    }

    private byte[] getPictureBytes(URI pictureUri) {
        return restTemplate.getForObject(pictureUri, byte[].class);
    }

    private Map.Entry<URI, Long> getPictureFinalUriAndSize(Picture picture) {
        HttpHeaders headers = restTemplate.headForHeaders(picture.ingSrc());
        URI location = headers.getLocation();
        URI finalUri = picture.ingSrc();

        while (location != null) {
            finalUri = location;
            headers = restTemplate.headForHeaders(location);
            location = headers.getLocation();
        }

        return new AbstractMap.SimpleEntry<>(finalUri, headers.getContentLength());
    }
}
