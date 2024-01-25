package com.senfiles.version1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senfiles.version1.Model.RoleModel;


public interface RoleRepository extends JpaRepository<RoleModel, Long> {
   
}