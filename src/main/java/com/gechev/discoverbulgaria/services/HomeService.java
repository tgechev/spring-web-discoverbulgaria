package com.gechev.discoverbulgaria.services;

import java.util.List;

public interface HomeService {
    List getCards(String regionId, String cat, String stringType);
}
