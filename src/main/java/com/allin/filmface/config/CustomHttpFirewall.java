package com.allin.filmface.config;

import org.springframework.security.web.firewall.FirewalledRequest;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.http.HttpServletRequest;

public class CustomHttpFirewall extends StrictHttpFirewall {

    public CustomHttpFirewall() {
        setAllowUrlEncodedPercent(true);
        setAllowUrlEncodedSlash(true);
        setAllowSemicolon(true);
        setAllowUrlEncodedDoubleSlash(true);
        setAllowUrlEncodedPeriod(true);
        setAllowBackSlash(true);
    }

    @Override
    public FirewalledRequest getFirewalledRequest(HttpServletRequest request) throws RequestRejectedException {
        try {
            return super.getFirewalledRequest(request);
        } catch (RequestRejectedException e) {
            if (e.getMessage().contains("The request was rejected because the URL contained a potentially malicious String \"%0A\"")) {
                return new FirewalledRequestWrapper(request);
            } else {
                throw e;
            }
        }
    }
}
