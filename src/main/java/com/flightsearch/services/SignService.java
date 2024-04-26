package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
import com.flightsearch.models.SignStatus;
import com.flightsearch.repositories.SignRepository;
import com.flightsearch.schemas.document.SignRead;
import com.flightsearch.services.mapping.SignMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class SignService {
    final SignRepository signRepository;
    final SignMapper signMapper;
    final SecurityService securityService;

    public SignRead confirm(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id, "Sign")
        );
        securityService.userRequired(sign.getCounterpart());
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setSignStatus(SignStatus.CONFIRMED);
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
