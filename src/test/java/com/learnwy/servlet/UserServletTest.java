package com.learnwy.servlet;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.junit.Test;
import sun.rmi.runtime.Log;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-18.
 */
public class UserServletTest {
    @Test
    public void SQL() {
        try {
            ServletRunner servletRunner = new ServletRunner();
            servletRunner.registerServlet("user", UserServlet.class.getName());
            servletRunner.registerServlet("login", LoginServlet.class.getName());
            ServletUnitClient servletUnitClient = servletRunner.newClient();
            WebRequest webRequest = new PostMethodWebRequest("http://localhost/login");
            webRequest.setParameter("name", "name");
            webRequest.setParameter("pwd", "pwd");
            WebRequest request = new PostMethodWebRequest("http://localhsot/user");

            request.setParameter("action", "");
            request.setParameter("id", "1");
            request.setParameter("display_name", "wangyang");
            request.setParameter("name", "name ");
            request.setParameter("pwd", "pwd  ");
            request.setParameter("page", "1");
            try {
                InvocationContext invocationContext_login = servletUnitClient.newInvocation(webRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                InvocationContext invocationContext = servletUnitClient.newInvocation(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
            WebResponse response = servletUnitClient.getResponse(request);
            System.out.print(response.getText());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
