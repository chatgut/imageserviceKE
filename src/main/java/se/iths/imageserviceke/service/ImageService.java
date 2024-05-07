package se.iths.imageserviceke.service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import se.iths.imageserviceke.entity.Image;
import se.iths.imageserviceke.repository.ImageRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {
    private final int MAX_WIDTH = 180;
    private final int MAX_HEIGHT = 200;
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

    public byte[] thumbnailiator(String id) throws IOException {
        var image = findById(id);
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(image.getImageBytes()));
        var thumbNailStream = new ByteArrayOutputStream();
        Thumbnails.of(originalImage).size(MAX_WIDTH, MAX_HEIGHT).outputFormat(image.getContentType().split("/")[1]).toOutputStream(thumbNailStream);

        return thumbNailStream.toByteArray();
    }


}
