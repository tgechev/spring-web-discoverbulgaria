package com.gechev.discoverbulgaria.web.controllers;

import com.gechev.discoverbulgaria.services.*;
import com.gechev.discoverbulgaria.services.models.*;
import com.gechev.discoverbulgaria.util.FileUtil;
import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class SeedController implements CommandLineRunner {
    private final static String ROLES_JSON = "\\src\\main\\resources\\files\\json\\roles.json";
    private final static String USERS_JSON = "\\src\\main\\resources\\files\\json\\users.json";
    private final static String REGIONS_JSON = "\\src\\main\\resources\\files\\json\\regions.json";
    private final static String FACTS_JSON = "\\src\\main\\resources\\files\\json\\facts.json";
    private final static String POI_JSON = "\\src\\main\\resources\\files\\json\\poi.json";
    private final RoleService roleService;
    private final UserService userService;
    private final RegionService regionService;
    private final FactService factService;
    private final PoiService poiService;
    private final Gson gson;
    private final FileUtil fileUtil;

    public SeedController(RoleService roleService, UserService userService, RegionService regionService, FactService factService, PoiService poiService, Gson gson, FileUtil fileUtil) {
        this.roleService = roleService;
        this.userService = userService;
        this.regionService = regionService;
        this.factService = factService;
        this.poiService = poiService;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void run(String... args) throws Exception {
//        seedRoles();
//        seedUsers();
//        seedRegions();
//        seedFacts();
        seedPoi();
    }

    private void seedRoles() throws IOException {
        String content = this.fileUtil.readFile(ROLES_JSON);
        RoleServiceModel[] roleServiceModels  = this.gson.fromJson(content, RoleServiceModel[].class);
        this.roleService.seedRoles(roleServiceModels);
    }

    private void seedUsers() throws IOException {
        String content = this.fileUtil.readFile(USERS_JSON);
        UserServiceModel[] userServiceModels = this.gson.fromJson(content, UserServiceModel[].class);
        this.userService.seedUsers(userServiceModels);
    }

    private void seedRegions() throws IOException {
        String content = this.fileUtil.readFile(REGIONS_JSON);
        RegionServiceModel[] regionServiceModels = this.gson.fromJson(content, RegionServiceModel[].class);
        this.regionService.seedRegions(regionServiceModels);
    }

    private void seedFacts() throws IOException {
        String content = this.fileUtil.readFile(FACTS_JSON);
        FactServiceModel[] factServiceModels = this.gson.fromJson(content, FactServiceModel[].class);
        this.factService.seedFacts(factServiceModels);
    }

    private void seedPoi() throws IOException {
        String content = this.fileUtil.readFile(POI_JSON);
        PoiServiceModel[] poiServiceModels = this.gson.fromJson(content, PoiServiceModel[].class);
        this.poiService.seedPoi(poiServiceModels);
    }
}
