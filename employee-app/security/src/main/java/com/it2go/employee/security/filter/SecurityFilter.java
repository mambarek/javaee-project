package com.it2go.employee.security.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        final String name = httpServletRequest.getUserPrincipal().getName();
        final String remoteUser = httpServletRequest.getRemoteUser();
        final Principal userPrincipal = httpServletRequest.getUserPrincipal();
        //userPrincipal.

    }

    @Override
    public void destroy() {

    }
}
