package io.github.sanyavertolet.interview;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a spreadsheet document stored in the "spreadsheet" MongoDB collection.
 *
 * <p>This class is annotated with {@link Document}, making it a managed entity in MongoDB.
 * It contains fields for the spreadsheet's unique ID, name, creator, and data. Additionally,
 * it provides utility methods for converting the spreadsheet into metadata or a DTO
 * ({@link SpreadsheetDto}) for use in other parts of the application.</p>
 */
@SuppressWarnings("unused")
@Document(collection = "spreadsheet")
public class Spreadsheet {
    @Id
    private String id;
    private String name;
    private String creator;
    private String data;

    /**
     * Default constructor.
     *
     * <p>Creates a new, empty {@code Spreadsheet} instance. This constructor is primarily
     * used for deserialization or when fields are set dynamically after instantiation.</p>
     */
    public Spreadsheet() { }

    /**
     * Constructs a new {@code Spreadsheet} instance with the specified name, creator, and data.
     *
     * @param name    the name of the spreadsheet.
     * @param creator the creator of the spreadsheet.
     * @param data    the serialized content of the spreadsheet.
     */
    public Spreadsheet(String name, String creator, String data) {
        this.name = name;
        this.creator = creator;
        this.data = data;
    }

    /**
     * Returns the unique identifier of the spreadsheet.
     *
     * @return the spreadsheet's unique ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the name of the spreadsheet.
     *
     * @return the name of the spreadsheet.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the serialized data content of the spreadsheet.
     *
     * @return the serialized content of the spreadsheet.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the unique identifier for the spreadsheet.
     *
     * @param id the unique ID to set for the spreadsheet.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the name of the spreadsheet.
     *
     * @param name the name to set for the spreadsheet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the serialized data content of the spreadsheet.
     *
     * @param data the serialized content to set for the spreadsheet.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Converts the current spreadsheet into its metadata representation.
     *
     * @return a {@link SpreadsheetMetadata} instance containing the spreadsheet's metadata.
     */
    public SpreadsheetMetadata getMetadata() {
        return new SpreadsheetMetadata(id, name, creator);
    }

    /**
     * Converts the current spreadsheet into a data transfer object (DTO).
     *
     * <p>This method combines the spreadsheet's metadata and serialized data into a
     * {@link SpreadsheetDto} for use in transferring the spreadsheet's full details
     * across different layers of the application.</p>
     *
     * @return a {@link SpreadsheetDto} instance representing the spreadsheet.
     */
    public SpreadsheetDto toDto() {
        SpreadsheetMetadata metadata = getMetadata();
        return new SpreadsheetDto(metadata, data);
    }
}
