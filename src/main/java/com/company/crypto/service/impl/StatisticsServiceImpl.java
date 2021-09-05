package com.company.crypto.service.impl;

import com.binance.api.client.BinanceApiWebSocketClient;
import com.company.crypto.dto.TransactionDto;
import com.company.crypto.entity.Statistics;
import com.company.crypto.entity.User;
import com.company.crypto.repository.StatisticsRepository;
import com.company.crypto.repository.UserRepository;
import com.company.crypto.service.AuthorizationService;
import com.company.crypto.service.StatisticsService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final BinanceApiWebSocketClient webSocketClient;
    private final AuthorizationService authorizationService;
    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;


    @Override
    public Double calculatePNL() {
        String username = authorizationService.getProfileOfCurrent().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Credentials"));
        List<Statistics> statistics = statisticsRepository.findAllByUser(user);
        if (statistics.size() == 0) {
            log.info(String.format("%s PL = %s",username,0.0));
            return 0.0;
        }
        Double totalPL = statistics.stream().map(Statistics::getPl).reduce(Double::sum)
                .orElseThrow(() -> new EntityNotFoundException("No PL"));
        log.info(String.format("%s PL = %s",username,totalPL/statistics.size()));

        return totalPL/statistics.size();
    }
}