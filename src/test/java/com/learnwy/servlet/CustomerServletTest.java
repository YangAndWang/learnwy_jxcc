package com.learnwy.servlet;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by 25973 on 2017-05-18.
 */
public class CustomerServletTest {
    @Test
    public void SQL() {
        try {
            ServletRunner servletRunner = new ServletRunner();
            servletRunner.registerServlet("customer", CustomerServlet.class.getName());
            ServletUnitClient servletUnitClient = servletRunner.newClient();

            WebRequest request = new PostMethodWebRequest("http://localhsot:8080/cuseomer");
            request.setParameter("name", "name ;delete from user; ");
            request.setParameter("pwd", "pwd  ");

            InvocationContext invocationContext = servletUnitClient.newInvocation(request);
            LoginServlet loginServlet = (LoginServlet) invocationContext.getServlet();

            WebResponse response = servletUnitClient.getResponse(request);

            System.out.print(response.getText());


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}