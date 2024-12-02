package com.java.eventtracker.utils;

import java.util.Map;

public interface IAuthenticatedUserService {
    Long getUserId();

    Map<String, Object> getAttributes();
}