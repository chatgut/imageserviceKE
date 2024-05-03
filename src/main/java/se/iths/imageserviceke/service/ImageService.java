package se.iths.imageserviceke.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import se.iths.imageserviceke.entity.Image;
import se.iths.imageserviceke.repository.ImageRepository;

import java.io.IOException;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public long saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setImageBytes(file.getBytes());
        image.setContentType(file.getContentType());
        image.setName(file.getName());
        imageRepository.save(image);

        return image.getId();
    }
}
