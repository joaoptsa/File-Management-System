package com.senfiles.version1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senfiles.version1.Model.Logs;

public interface LogsRepository extends JpaRepository<Logs, Long> {

}