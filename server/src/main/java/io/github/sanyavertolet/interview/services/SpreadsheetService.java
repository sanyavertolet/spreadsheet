package io.github.sanyavertolet.interview.services;

import io.github.sanyavertolet.interview.Spreadsheet;
import io.github.sanyavertolet.interview.SpreadsheetDto;
import io.github.sanyavertolet.interview.SpreadsheetMetadata;
import io.github.sanyavertolet.interview.repositories.SpreadsheetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SpreadsheetService {
    private final SpreadsheetRepository spreadsheetRepository;

    public SpreadsheetService(SpreadsheetRepository spreadsheetRepository) {
        this.spreadsheetRepository = spreadsheetRepository;
    }
    public List<SpreadsheetMetadata> getList() {
        return spreadsheetRepository.findAll().stream().map(Spreadsheet::getMetadata).toList();
    }

    public Optional<Spreadsheet> getById(String id) {
        return spreadsheetRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(String id) {
        if (spreadsheetRepository.findById(id).isEmpty()) {
            return false;
        }
        spreadsheetRepository.deleteById(id);
        return true;
    }

    @Transactional
    public Optional<Spreadsheet> updateById(String id, String data) {
        Optional<Spreadsheet> spreadsheetOptional = spreadsheetRepository.findById(id);
        if (spreadsheetOptional.isEmpty()) {
            return Optional.empty();
        }
        Spreadsheet spreadsheet = spreadsheetOptional.get();
        spreadsheet.setData(data);
        return Optional.of(spreadsheetRepository.save(spreadsheet));
    }

    public Spreadsheet save(SpreadsheetDto spreadsheetDto) {
        SpreadsheetMetadata metadata = spreadsheetDto.getMetadata();
        Spreadsheet spreadsheet = new Spreadsheet(
                metadata.getName(),
                metadata.getCreator(),
                spreadsheetDto.getData());
        return spreadsheetRepository.save(spreadsheet);
    }
}
