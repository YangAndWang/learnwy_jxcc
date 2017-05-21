package com.learnwy.servlet;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.MessageHeader;
import org.junit.Test;

import javax.jws.WebResult;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-18.
 */
public class LoginServletTest {
    @Test
    public void testLoginServlet() {
        try {
            ServletRunner servletRunner = new ServletRunner();
            servletRunner.registerServlet("login", LoginServlet.class.getName());
            ServletUnitClient servletUnitClient = servletRunner.newClient();

            WebRequest request = new PostMethodWebRequest("http://localhsot:8080/login");
//            request.setParameter("name", "name");
//            request.setParameter("pwd", "pwd");
            request.setParameter("name", "admin ");
            request.setParameter("pwd", "admin ");

            InvocationContext invocationContext = servletUnitClient.newInvocation(request);
            LoginServlet loginServlet = (LoginServlet) invocationContext.getServlet();

            WebResponse response = servletUnitClient.getResponse(request);

            System.out.print(response.getText());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}