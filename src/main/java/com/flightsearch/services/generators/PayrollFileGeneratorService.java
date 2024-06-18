package com.flightsearch.services.generators;

import com.flightsearch.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PayrollFileGeneratorService {
    private final FileRepository fileRepository;

    // todo: переделать
//    private String generatePayrollFileContent(User beneficiary) {
//        return "Платежная ведомость на имя " + beneficiary.getFullName();
//    }

//    public FileInfo generatePayrollFile(User beneficiary) {
//        return fileRepository.saveFile(
//                "payroll",
//                "payroll_for_" + beneficiary.getId() + ".txt",
//                generatePayrollFileContent(beneficiary)
//        );
//    }
}
