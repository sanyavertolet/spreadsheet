package io.github.sanyavertolet.interview.controllers;

import io.github.sanyavertolet.interview.SheetData;
import io.github.sanyavertolet.interview.services.SheetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/sheets")
@RestController
public class SheetController {
    private final SheetService sheetService;

    public SheetController(SheetService sheetService) {
        this.sheetService = sheetService;
    }

    @GetMapping(value = "/list")
    public List<String> list() {
        return sheetService.getList();
    }

    @PostMapping(value = "/save")
    public SheetData save(@RequestBody SheetData sheetData) {
        return sheetService.save(sheetData);
    }
}
