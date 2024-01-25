package com.senfiles.version1.repository;

import com.senfiles.version1.Model.File;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<File, Long> {
    Optional<File> findByName(String name);

}