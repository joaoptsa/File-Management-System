package com.senfiles.version1.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.senfiles.version1.Model.Logs;
import com.senfiles.version1.repository.LogsRepository;

import jakarta.transaction.Transactional;

@Service
public class LogsService {

    @Autowired
    private LogsRepository logsRepository;

    @Transactional
    public void LogsSave(Logs logs) {
        try {
            logsRepository.save(logs);
        } catch (DataAccessException e) {
            return;
        }
    }

    public List<Logs> getFiles() {
        List<Logs> logs = logsRepository.findAll();
        return logs;
    }

    public String getLogsTable(List<Logs> logs) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Failed authentication\n\n"));
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("%-20s %-20s%n", "IP", "Date "));

        for (Logs log : logs) {
            sb.append(String.format("%-20s %-20s%n", log.getIp(), log.getCreatedAt()));
        }
        return sb.toString();
    }

}
