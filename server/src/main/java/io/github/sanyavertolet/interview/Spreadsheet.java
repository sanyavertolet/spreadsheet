package io.github.sanyavertolet.interview;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spreadsheet")
public class Spreadsheet {
    @Id
    private String id;
    private String name;
    private String creator;
    private String data;

    public Spreadsheet() { }

    public Spreadsheet(String name, String creator, String data) {
        this.name = name;
        this.creator = creator;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(String data) {
        this.data = data;
    }

    public SpreadsheetMetadata getMetadata() {
        return new SpreadsheetMetadata(id, name, creator);
    }

    public SpreadsheetDto toDto() {
        SpreadsheetMetadata metadata = getMetadata();
        return new SpreadsheetDto(metadata, data);
    }
}
