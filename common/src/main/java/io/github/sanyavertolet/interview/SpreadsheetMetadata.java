package io.github.sanyavertolet.interview;

/**
 * Represents metadata for a spreadsheet in the application.
 *
 * <p>This class encapsulates essential information about a spreadsheet, including its name, creator,
 * and unique identifier. It provides getter and setter methods for accessing and modifying these
 * properties. The metadata is used for identifying and managing spreadsheets in various parts of
 * the application, such as during save or load operations.</p>
 */
@SuppressWarnings("unused")
public class SpreadsheetMetadata {

    private String name;    // The name of the spreadsheet
    private String creator; // The name of the person who created the spreadsheet
    private String id;      // A unique identifier for the spreadsheet

    /**
     * Default constructor.
     *
     * <p>Creates a new, empty {@code SpreadsheetMetadata} instance. This constructor is primarily
     * used for deserialization or when the metadata values are set later using setters.</p>
     */
    public SpreadsheetMetadata() { }

    /**
     * Constructs a new {@code SpreadsheetMetadata} instance with the specified properties.
     *
     * @param id      the unique identifier for the spreadsheet.
     * @param name    the name of the spreadsheet.
     * @param creator the name of the creator of the spreadsheet.
     */
    public SpreadsheetMetadata(String id, String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.id = id;
    }

    /**
     * Returns the name of the spreadsheet.
     *
     * @return the spreadsheet's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the creator of the spreadsheet.
     *
     * @return the name of the creator.
     */
    public String getCreator() {
        return creator;
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
     * Sets the name of the spreadsheet.
     *
     * @param name the name to set for the spreadsheet.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the creator of the spreadsheet.
     *
     * @param creator the name of the creator to set.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Sets the unique identifier for the spreadsheet.
     *
     * @param id the unique ID to set for the spreadsheet.
     */
    public void setId(String id) {
        this.id = id;
    }
}
