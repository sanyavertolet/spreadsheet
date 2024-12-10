package io.github.sanyavertolet.interview.services;

import io.github.sanyavertolet.interview.SheetData;
import io.github.sanyavertolet.interview.repositories.SheetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetService {
    private final SheetRepository sheetRepository;

    public SheetService(SheetRepository sheetRepository) {
        this.sheetRepository = sheetRepository;
    }
    public List<String> getList() {
        return List.of();
    }

    public SheetData save(SheetData sheetData) {
        return sheetRepository.save(sheetData);
    }
}
