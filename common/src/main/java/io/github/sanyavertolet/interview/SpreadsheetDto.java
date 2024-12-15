package io.github.sanyavertolet.interview;

/**
 * A Data Transfer Object (DTO) for representing a spreadsheet along with its metadata and data content.
 *
 * <p>This class is used to encapsulate the full details of a spreadsheet, including its metadata
 * (e.g., name, creator, and ID) and the actual data contained within the spreadsheet. It serves as
 * a lightweight container for transferring spreadsheet information between layers of the application
 * or over the network.</p>
 */
@SuppressWarnings("unused")
public class SpreadsheetDto {

    private SpreadsheetMetadata metadata; // Metadata containing details like name, creator, and ID
    private String data;                  // The spreadsheet's content in serialized form

    /**
     * Default constructor.
     *
     * <p>Creates a new, empty {@code SpreadsheetDto} instance. This constructor is primarily
     * used for deserialization or when the properties are set later using setters.</p>
     */
    public SpreadsheetDto() { }

    /**
     * Constructs a new {@code SpreadsheetDto} instance with the specified metadata and data.
     *
     * @param metadata the metadata of the spreadsheet, including name, creator, and ID.
     * @param data     the actual content of the spreadsheet.
     */
    public SpreadsheetDto(SpreadsheetMetadata metadata, String data) {
        this.metadata = metadata;
        this.data = data;
    }

    /**
     * Returns the metadata of the spreadsheet.
     *
     * @return the spreadsheet's {@link SpreadsheetMetadata}.
     */
    public SpreadsheetMetadata getMetadata() {
        return metadata;
    }

    /**
     * Returns the data content of the spreadsheet.
     *
     * @return the spreadsheet's data as a {@link String}.
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the metadata of the spreadsheet.
     *
     * @param metadata the {@link SpreadsheetMetadata} to set for the spreadsheet.
     */
    public void setMetadata(SpreadsheetMetadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Sets the data content of the spreadsheet.
     *
     * @param data the data content to set for the spreadsheet.
     */
    public void setData(String data) {
        this.data = data;
    }
}
