package com.kzh.system.login;

import com.kzh.system.security.MyUserDetailServiceImpl;
import com.kzh.util.struts.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.ResultPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

@ResultPath("/")
public class LoginAction extends BaseAction {
    private String userName;
    private String password;
    @Autowired
    private MyUserDetailServiceImpl userDetailService;

    public String execute() {
        ServletRequest request = this.getRequest();

        return SUCCESS;
    }

    public String login() {
        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        HttpSession session = getRequest().getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        return SUCCESS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MyUserDetailServiceImpl getUserDetailService() {
        return userDetailService;
    }

    public void setUserDetailService(MyUserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
}
