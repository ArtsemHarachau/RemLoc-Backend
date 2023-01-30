package com.example.postgresql.services;

import com.example.postgresql.repositories.AdministratorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorsService {

    @Autowired
    private AdministratorsRepository administratorsRepository;
}
