package com.flightsearch.services.delegates;

import com.flightsearch.models.Document;
import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.FileInfoRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.SignSchema;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class SaveDocumentDelegate implements JavaDelegate {
    @Autowired
    FileInfoRepository fileInfoRepository;
    @Autowired
    DocumentRepository docRepository;
    @Autowired
    SignRepository signRepository;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
//        delegateExecution.getProcessEngineServices().getIdentityService().createUserQuery().orderByUserId().list();
        Document newDoc = mapDocumentFormToEntity(delegateExecution);
        docRepository.save(newDoc);
        List<Sign> signs = signRepository.saveAll(newDoc.getSigns());
        delegateExecution.setVariable("ownerId", getCurrentUser(delegateExecution));
        delegateExecution.setVariable("documentId", newDoc.getId());
        delegateExecution.setVariable(
                "signs",
                signs.stream().map(
                        sign -> new SignSchema(sign.getUserId(), sign.getId())
                ).toList()
        );
    }

    private String getCurrentUser(DelegateExecution delegateExecution) {
        return delegateExecution.getProcessEngineServices()
                .getIdentityService().getCurrentAuthentication().getUserId();
    }

    private FileInfo getDocumentFile(DelegateExecution delegateExecution) {
        UUID fileId = (UUID) delegateExecution.getVariable("fileId");
        return fileInfoRepository.findById(fileId)
                .orElseThrow(() -> new BpmnError("NotFound"));
    }

    private List<String> getSignerIds(DelegateExecution delegateExecution) {
        String[] signsIds = ((String) delegateExecution.getVariable("signerIds")).split(",");
        return Arrays.asList(signsIds);
    }

    private Document mapDocumentFormToEntity(DelegateExecution delegateExecution) {
        Document newDoc = new Document();
        newDoc.setOwnerId(getCurrentUser(delegateExecution));
        List<Sign> signs = getSignerIds(delegateExecution).stream()
                .map((signsId) -> {
                    Sign sign = new Sign();
                    sign.setUserId(signsId);
                    sign.setDocument(newDoc);
                    return sign;
                }).toList();
        newDoc.setSigns(signs);
        newDoc.setFile(getDocumentFile(delegateExecution));
        newDoc.setTitle((String) delegateExecution.getVariable("title"));
        newDoc.setDescription((String) delegateExecution.getVariable("description"));
        Date deadline = (Date) delegateExecution.getVariable("deadlineDate");
        if (deadline != null) {
            newDoc.setDeadline(new Timestamp(deadline.getTime()));
        }
        return newDoc;
    }
}
