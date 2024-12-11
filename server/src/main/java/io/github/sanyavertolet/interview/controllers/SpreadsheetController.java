package io.github.sanyavertolet.interview.controllers;

import io.github.sanyavertolet.interview.Spreadsheet;
import io.github.sanyavertolet.interview.SpreadsheetDto;
import io.github.sanyavertolet.interview.SpreadsheetMetadata;
import io.github.sanyavertolet.interview.services.SpreadsheetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/sheets")
@RestController
public class SpreadsheetController {
    private final SpreadsheetService spreadsheetService;

    public SpreadsheetController(SpreadsheetService spreadsheetService) {
        this.spreadsheetService = spreadsheetService;
    }

    @GetMapping
    public List<SpreadsheetMetadata> list() {
        return spreadsheetService.getList();
    }

    @GetMapping(value = "/{id}")
    public Optional<SpreadsheetDto> getById(@PathVariable String id) {
        return spreadsheetService.getById(id).map(Spreadsheet::toDto);
    }

    @PostMapping(value = "/{id}")
    public Optional<SpreadsheetDto> updateById(@PathVariable String id, @RequestBody String data) {
        return spreadsheetService.updateById(id, data).map(Spreadsheet::toDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        boolean isDeleted = spreadsheetService.deleteById(id);
        if (isDeleted) {
            String successMessage = String.format("Successfully deleted spreadsheet with id %s.", id);
            return ResponseEntity.ok(successMessage);
        } else {
            String errorMessage = String.format("Could not find spreadsheet with id %s.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @PostMapping(value = "/new")
    public SpreadsheetDto save(@RequestBody SpreadsheetDto spreadsheetDto) {
        return spreadsheetService.save(spreadsheetDto).toDto();
    }
}
