package com.it2go.employee.security.filter;

import com.it2go.employee.entities.ApplicationUser;
import com.it2go.employee.entities.Person;
import com.it2go.employee.ui.controller.LoginController;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

    @Inject
    private Instance<LoginController> loginController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println(">> SecurityFilter Init...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        //final String name = httpServletRequest.getUserPrincipal().getName();
        final String remoteUser = httpServletRequest.getRemoteUser();
        final Principal userPrincipal = httpServletRequest.getUserPrincipal();
        //userPrincipal.
        if(userPrincipal == null)
            System.out.println(">> SecurityFilter userPrincipal is null !!! ");
        else {
            LoginController controller = loginController.get();
            if(controller.getLoggedInUser() == null) {
                ApplicationUser user = new ApplicationUser();
                user.setUserName(userPrincipal.getName());
                controller.setLoggedInUser(user);
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
