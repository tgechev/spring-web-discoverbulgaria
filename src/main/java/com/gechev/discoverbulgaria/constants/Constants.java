package com.gechev.discoverbulgaria.constants;

public final class Constants {

    private static final String USER_DIR = System.getProperty("user.dir");
    public static final String RESOURCES_DIR = USER_DIR + "\\src\\main\\resources\\";
    public final static String ROLES_JSON = RESOURCES_DIR + "files\\json\\roles.json";
    public final static String USERS_JSON = RESOURCES_DIR + "files\\json\\users.json";
    public final static String REGIONS_JSON = RESOURCES_DIR + "files\\json\\regions.json";
    public final static String FACTS_JSON = RESOURCES_DIR + "files\\json\\facts.json";
    public final static String POI_JSON = RESOURCES_DIR + "files\\json\\poi.json";
    public final static String CLOUDINARY_BASE_URL = "https://res.cloudinary.com/discover-bulgaria/image/upload/";
}
