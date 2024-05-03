package se.iths.imageserviceke.repository;

import org.springframework.data.repository.ListCrudRepository;
import se.iths.imageserviceke.entity.Image;

public interface ImageRepository extends ListCrudRepository<Image, Integer> {
}
