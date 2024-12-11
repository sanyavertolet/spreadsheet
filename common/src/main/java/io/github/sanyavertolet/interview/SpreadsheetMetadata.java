package io.github.sanyavertolet.interview;

public class SpreadsheetMetadata {
    private String name;
    private String creator;
    private String id;

    public SpreadsheetMetadata() { }

    public SpreadsheetMetadata(String id, String name, String creator) {
        this.name = name;
        this.creator = creator;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setId(String id) {
        this.id = id;
    }
}
