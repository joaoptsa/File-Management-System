package com.senfiles.version1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senfiles.version1.Dto.FileDto;
import com.senfiles.version1.Model.File;
import com.senfiles.version1.repository.FilesRepository;

import jakarta.transaction.Transactional;

@Service
public class FileService {

    @Autowired
    private FilesRepository filesRepository;

    public Optional<File> findByName(String name) {
        return filesRepository.findByName(name);
    }

    @Transactional
    public void delete(File project) {
        filesRepository.delete(project);

    }

    public List<FileDto> getFiles() {
        List<File> files = filesRepository.findAll();
        List<FileDto> dtos = new ArrayList<>();

        for (File file : files) {
            FileDto dto = new FileDto(file.getId(), file.getName(), file.getCreatedAt());
            dtos.add(dto);
        }

        return dtos;
    }

    public Optional<File> find_id(Long id) {
        return filesRepository.findById(id);

    }

    @Transactional
    public void Filesave(File project) {
        filesRepository.save(project);
    }

}
