package com.flightsearch.services.delegates;

import com.flightsearch.models.FileInfo;
import com.flightsearch.repositories.FileRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveDocumentFileDelegate implements JavaDelegate {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        FileInfo fileInfo = fileRepository.saveFile(
                "documents",
                "document",
                (String) delegateExecution.getVariable("fileText")
        );
        delegateExecution.setVariable("fileId", fileInfo.getId());
    }
}
