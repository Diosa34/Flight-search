package com.flightsearch.services;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.Sign;
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

    public SignRead confirm(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        sign.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        sign.setIsCounterpartSigned(true);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public SignRead removeSign(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        sign.setSubmitTime(null);
        sign.setIsCounterpartSigned(false);
        sign = signRepository.save(sign);
        return signMapper.mapEntityToSignRead(sign);
    }

    public void delete(Long id) {
        Sign sign = signRepository.findById(id).orElseThrow(NotFoundException::new);
        signRepository.delete(sign);
    }
}
