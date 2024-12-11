package io.github.sanyavertolet.interview;

public class SpreadsheetDto {
    private SpreadsheetMetadata metadata;
    private String data;

    public SpreadsheetDto() { }

    public SpreadsheetDto(SpreadsheetMetadata metadata, String data) {
        this.metadata = metadata;
        this.data = data;
    }

    public SpreadsheetMetadata getMetadata() {
        return metadata;
    }

    public String getData() {
        return data;
    }

    public void setMetadata(SpreadsheetMetadata metadata) {
        this.metadata = metadata;
    }

    public void setData(String data) {
        this.data = data;
    }
}
