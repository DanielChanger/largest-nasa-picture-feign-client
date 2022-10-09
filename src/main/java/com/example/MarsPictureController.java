package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mars/pictures")
@RequiredArgsConstructor
public class MarsPictureController {

    @Value("${nasa.api.key}")
    private String nasaApiKey;
    private final MarsPictureService marsPictureService;

    @GetMapping(value = "/largest", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] findLargestMarsPicture(@RequestParam int sol, @RequestParam(required = false) String camera) {
        return marsPictureService.findLargestMarsPicture(sol, camera, nasaApiKey);
    }
}
