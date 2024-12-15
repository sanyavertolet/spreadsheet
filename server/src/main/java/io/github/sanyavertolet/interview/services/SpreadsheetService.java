package io.github.sanyavertolet.interview.services;

import io.github.sanyavertolet.interview.Spreadsheet;
import io.github.sanyavertolet.interview.SpreadsheetDto;
import io.github.sanyavertolet.interview.SpreadsheetMetadata;
import io.github.sanyavertolet.interview.repositories.SpreadsheetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * A service class for managing spreadsheet operations.
 *
 * <p>This class provides a layer of abstraction between the controller and the repository,
 * handling business logic for retrieving, saving, updating, and deleting spreadsheet entities.
 * It interacts with the {@link SpreadsheetRepository} to perform database operations.</p>
 */
@Service
public class SpreadsheetService {
    private final SpreadsheetRepository spreadsheetRepository;

    /**
     * Constructs a new {@code SpreadsheetService} with the given repository.
     *
     * @param spreadsheetRepository the repository used for spreadsheet data access.
     */
    public SpreadsheetService(SpreadsheetRepository spreadsheetRepository) {
        this.spreadsheetRepository = spreadsheetRepository;
    }

    /**
     * Retrieves a list of all spreadsheet metadata.
     *
     * <p>This method queries the repository for all spreadsheets, extracts their metadata,
     * and returns it as a list.</p>
     *
     * @return a list of {@link SpreadsheetMetadata} representing all spreadsheets.
     */
    public List<SpreadsheetMetadata> getList() {
        return spreadsheetRepository.findAll()
                .stream()
                .map(Spreadsheet::getMetadata)
                .toList();
    }

    /**
     * Retrieves a spreadsheet by its ID.
     *
     * @param id the unique identifier of the spreadsheet to retrieve.
     * @return an {@link Optional} containing the spreadsheet if found, or empty if not.
     */
    public Optional<Spreadsheet> getById(String id) {
        return spreadsheetRepository.findById(id);
    }

    /**
     * Deletes a spreadsheet by its ID.
     *
     * <p>This method checks if the spreadsheet exists before attempting to delete it.
     * The operation is transactional to ensure consistency.</p>
     *
     * @param id the unique identifier of the spreadsheet to delete.
     * @return {@code true} if the spreadsheet was deleted, {@code false} if it was not found.
     */
    @Transactional
    public boolean deleteById(String id) {
        if (spreadsheetRepository.findById(id).isEmpty()) {
            return false;
        }
        spreadsheetRepository.deleteById(id);
        return true;
    }

    /**
     * Updates the data of a spreadsheet by its ID.
     *
     * <p>If the spreadsheet exists, this method updates its data field and saves the changes.
     * The operation is transactional to ensure consistency.</p>
     *
     * @param id   the unique identifier of the spreadsheet to update.
     * @param data the new data to set for the spreadsheet.
     * @return an {@link Optional} containing the updated spreadsheet if it was found, or empty if not.
     */
    @Transactional
    public Optional<Spreadsheet> updateById(String id, String data) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetRepository.findById(id);
        if (spreadsheetOptional.isEmpty()) {
            return Optional.empty();
        }
        Spreadsheet spreadsheet = spreadsheetOptional.get();
        spreadsheet.setData(data);
        return Optional.of(spreadsheetRepository.save(spreadsheet));
    }

    /**
     * Saves a new spreadsheet entity to the repository.
     *
     * <p>This method converts the given {@link SpreadsheetDto} into a {@link Spreadsheet} entity
     * and saves it to the database.</p>
     *
     * @param spreadsheetDto the data transfer object containing the spreadsheet's metadata and data.
     * @return the saved {@link Spreadsheet} entity.
     */
    public Spreadsheet save(SpreadsheetDto spreadsheetDto) {
        SpreadsheetMetadata metadata = spreadsheetDto.getMetadata();
        Spreadsheet spreadsheet = new Spreadsheet(
                metadata.getName(),
                metadata.getCreator(),
                spreadsheetDto.getData());
        return spreadsheetRepository.save(spreadsheet);
    }
}
