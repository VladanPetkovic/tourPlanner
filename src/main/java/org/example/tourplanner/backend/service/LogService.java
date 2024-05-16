package org.example.tourplanner.backend.service;

import org.example.tourplanner.backend.model.Log;
import org.example.tourplanner.backend.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAllLogs() {
        return logRepository.findAll();
    }

    public Log findLogById(Long id) {
        Optional<Log> log = logRepository.findById(id);
        return log.orElse(null);
    }

    public Log saveLog(Log log) {
        return logRepository.save(log);
    }

    public Log updateLog(Long id, Log logDetails) {
        Optional<Log> optionalLog = logRepository.findById(id);
        if (optionalLog.isPresent()) {
            Log log = optionalLog.get();
            log.setUsername(logDetails.getUsername());
            log.setDateTime(logDetails.getDateTime());
            log.setComment(logDetails.getComment());
            log.setDifficulty(logDetails.getDifficulty());
            log.setTotalDistance(logDetails.getTotalDistance());
            log.setTotalTime(logDetails.getTotalTime());
            log.setRating(logDetails.getRating());
            return logRepository.save(log);
        } else {
            return null;
        }
    }

    public boolean deleteLog(Long id) {
        if (logRepository.existsById(id)) {
            logRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
