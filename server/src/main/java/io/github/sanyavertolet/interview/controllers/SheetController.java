package io.github.sanyavertolet.interview.controllers;

import io.github.sanyavertolet.interview.services.SheetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
