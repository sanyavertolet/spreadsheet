package io.github.sanyavertolet.interview.repositories;

import io.github.sanyavertolet.interview.Spreadsheet;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpreadsheetRepository extends MongoRepository<Spreadsheet, String> { }

