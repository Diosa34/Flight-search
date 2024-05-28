package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.FileRepository;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.SignRead;
import com.flightsearch.services.mapping.SignMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class SignService {
    final private SignRepository signRepository;
    final private SignMapper signMapper;
    final private SecurityService securityService;

    final private FileRepository fileRepository;

    @Transactional
    public SignRead confirm(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );

        securityService.userRequired(sign.getCounterpart());
        FileInfo fileInfo = fileRepository.saveFile(
                "signs",
                "sign-" + id + ".txt",
                String.format("Документ %s подписан пользоваетелем %s",
                        sign.getDocument().getTitle(),
                        sign.getCounterpart().getFullName())
        );

        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setFile(fileInfo);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public SignRead reject(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );
        securityService.userRequired(sign.getCounterpart());
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.REJECTED);
        sign.setFile(null);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public void delete(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );
        securityService.userRequired(sign.getDocument().getOwner());
        signRepository.delete(sign);
    }
}
