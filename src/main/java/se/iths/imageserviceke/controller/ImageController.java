package se.iths.imageserviceke.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imageserviceke.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {
    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    String saveImage(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        long id = imageService.saveImage(file);
        String baseUrl = request.getRequestURL().toString();
        return baseUrl + id;
    }

    /*@GetMapping("/{id}")
    ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        Todo:GetMapping for id
    }*/

}
