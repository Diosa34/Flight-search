package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Document;
import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.DocumentRepository;
import com.flightsearch.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository docRepository;
    private final FileRepository fileRepository;

    public FileRepository.FileResource getDocumentsFileResource(Long documentId) {
        Document doc = docRepository.findById(documentId).orElseThrow(
                () -> new NotFoundException(documentId, "Document")
        );
        List<FileInfo> fileInfos = doc.getSigns().stream()
                .filter(sign -> sign.getSignStatus() == SignStatus.CONFIRMED)
                .map(Sign::getFile)
                .collect(Collectors.toList());
        fileInfos.add(doc.getFile());
        FileInfo[] fileInfosArray = new FileInfo[fileInfos.size()];
        return fileRepository.getZipResource("document", fileInfos.toArray(fileInfosArray));
    }
}
