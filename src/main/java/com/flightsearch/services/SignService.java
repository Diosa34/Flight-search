package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.exceptions.PermissionDeniedException;
import com.flightsearch.models.FileInfo;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.SignRead;
import com.flightsearch.services.generators.SignFileGeneratorService;
import com.flightsearch.services.mapping.SignMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"prodMain", "devMain"})
@RequiredArgsConstructor
public class SignService {
    private final SignRepository signRepository;
    private final SignFileGeneratorService signFileGeneratorService;
    private final SignMapper signMapper;
    private final SecurityService securityService;

    public SignRead confirm(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );

        securityService.userRequired(sign.getCounterpart());
        if (sign.getSignStatus() == SignStatus.MISSED_DEADLINE) {
            throw new PermissionDeniedException("Cрок подписания истёк", "User");
        }
        FileInfo signFile = signFileGeneratorService.generateSignFile(sign);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
        sign.setFile(signFile);
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

    public void setStatus(Long id, SignStatus signStatus) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );
        sign.setSignStatus(signStatus);
        sign = signRepository.save(sign);
        signMapper.mapEntityToSignRead(sign);
    }

    public Set<SignRead> getSignsByCounterpartId(Long counterpartId) {
        return signRepository.findAllByCounterpartId(counterpartId).orElseThrow(
                        () -> new NotFoundException(counterpartId, "Sign"))
                .stream()
                .map(signMapper::mapEntityToSignRead)
                .collect(Collectors.toSet());
    }

    public void delete(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );
        securityService.userRequired(sign.getDocument().getOwner());
        signRepository.delete(sign);
    }
}
