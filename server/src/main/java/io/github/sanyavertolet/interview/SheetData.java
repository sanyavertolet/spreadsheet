package io.github.sanyavertolet.interview;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "spreadsheet")
public class SheetData {
    @Id
    private Integer id;
    private String name;

    public SheetData() { }
    public SheetData(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
