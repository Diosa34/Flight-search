package com.flightsearch.services.generators;

import com.flightsearch.models.FileInfo;
import com.flightsearch.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PayrollFileGeneratorService {
    private final FileRepository fileRepository;

    private String generatePayrollFileContent(String beneficiaryId) {
        return "Платежная ведомость на имя " + beneficiaryId;
    }

    public FileInfo generatePayrollFile(String beneficiaryId) {
        return fileRepository.saveFile(
                "payroll",
                "payroll_for_" + beneficiaryId + ".txt",
                generatePayrollFileContent(beneficiaryId)
        );
    }
}
