package io.github.sanyavertolet.interview.repositories;

import io.github.sanyavertolet.interview.Spreadsheet;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for performing CRUD operations on {@link Spreadsheet} documents.
 *
 * <p>This interface extends {@link MongoRepository}, which provides built-in methods for
 * common operations such as saving, finding, updating, and deleting documents in a MongoDB
 * collection. The generic parameters specify the entity type ({@link Spreadsheet}) and the
 * type of the entity's unique identifier ({@link String}).</p>
 *
 * <p>By default, Spring Data MongoDB generates the implementation for this interface
 * at runtime, allowing seamless integration with the database without requiring
 * boilerplate code.</p>
 */
public interface SpreadsheetRepository extends MongoRepository<Spreadsheet, String> { }
