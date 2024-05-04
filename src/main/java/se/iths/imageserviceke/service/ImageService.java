package se.iths.imageserviceke.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import se.iths.imageserviceke.entity.Image;
import se.iths.imageserviceke.repository.ImageRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public long saveImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setImageBytes(file.getBytes());
        image.setContentType(file.getContentType());
        image.setName(file.getName());
        imageRepository.save(image);

        return image.getId();
    }

    public Image findById(String id) {
        long imageId;

        try {
            imageId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isPresent())
            return image.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public void deleteById(String id) {
        imageRepository.delete(findById(id));
    }


}
