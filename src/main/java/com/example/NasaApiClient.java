package com.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.List;

@FeignClient(name = "nasa-api-client", url = "https://api.nasa.gov")
public interface NasaApiClient {

    @GetMapping("/mars-photos/api/v1/rovers/curiosity/photos")
    MarsPicturesResponse findAllPictures(@RequestParam int sol,
                                         @RequestParam(required = false) String camera,
                                         @RequestParam("api_key") String apiKey);

    record MarsPicturesResponse(@JsonProperty("photos") List<Picture> pictures) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Picture(int sol, Camera camera, @JsonProperty("img_src") URI ingSrc) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    record Camera(String name) {
    }

}
