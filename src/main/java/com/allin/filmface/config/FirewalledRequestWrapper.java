package com.allin.filmface.config;

import org.springframework.security.web.firewall.FirewalledRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class FirewalledRequestWrapper extends FirewalledRequest {

    public FirewalledRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void reset() {
        // Nothing to reset as it's a stateless request wrapper.
    }
}
