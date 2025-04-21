package com.app.ignouapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ignouapp.model.AdminInfo;

public interface AdminInfoRepository extends JpaRepository<AdminInfo, String> {

}
