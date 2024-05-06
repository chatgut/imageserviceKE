package se.iths.imageserviceke.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imageserviceke.service.ImageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/image")
@CrossOrigin
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    String saveMultipleImages(@RequestParam("img") Collection<MultipartFile> files,
                              HttpServletRequest request) throws IOException {
        Collection<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            long generatedId = imageService.saveImage(file);
            String baseUrl = request.getRequestURL().toString();
            urls.add(baseUrl + "/" + generatedId);
        }
        return urls.toString();
    }

    @GetMapping("/{id}")
    ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        var image = imageService.findById(id);
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, image.getContentType());
        return new ResponseEntity<>(image.getImageBytes(), httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteImage(@PathVariable("id") String id) {
        imageService.deleteById(id);
    }

}
