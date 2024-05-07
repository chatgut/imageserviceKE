package se.iths.imageserviceke.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imageserviceke.service.ImageService;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/images")
@CrossOrigin
public class ImageController {

    ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    String saveMultipleImages(@RequestParam("image") Collection<MultipartFile> files,
                              HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (MultipartFile file : files) {
            long generatedId = imageService.saveImage(file);
            String url = request.getRequestURL().toString() + "/" + generatedId;
            sb.append(url).append("\n");
        }
        return sb.toString();
    }

    @GetMapping("/{id}")
    ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
        var image = imageService.findById(id);
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, image.getContentType());
        return new ResponseEntity<>(image.getImageBytes(), httpHeaders, HttpStatus.OK);
    }

    @GetMapping("{id}/thumbnail")
    ResponseEntity<byte[]> getImageThumbnail(@PathVariable("id") String id) throws IOException {
        var httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, imageService.findById(id).getContentType());
        var thumbnail = imageService.thumbnailiator(id);
        return new ResponseEntity<>(thumbnail, httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteImage(@PathVariable("id") String id) {
        imageService.deleteById(id);
    }


}
