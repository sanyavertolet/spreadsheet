package io.github.sanyavertolet.interview.repositories;

import io.github.sanyavertolet.interview.SheetData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SheetRepository extends MongoRepository<SheetData, String> {
}

