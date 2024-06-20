package com.vuelosglobales.planes.adapters.out;

import  com.vuelosglobales.planes.domain.models.Plane;
import com.vuelosglobales.planes.infraestructure.PlaneRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaneMySQLRepository {
    private final String url;
    private final String user;
    private final String password;
    
    public PlaneMySQLRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    
}
