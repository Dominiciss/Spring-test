package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entities.Image;
import com.example.demo.Exceptions.ImageException;
import com.example.demo.Services.ImageService;

@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageService is;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> image(@PathVariable String id) {
        try {
            Image image = is.getId(id);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.IMAGE_JPEG);

            return new ResponseEntity<>(image.getContent(), headers, HttpStatus.OK);
        } catch (ImageException e) {
            return null;
        }
    }

}
