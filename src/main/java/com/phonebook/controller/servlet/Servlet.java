package com.phonebook.controller.servlet;

import com.phonebook.controller.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    @Autowired
    private Dispatcher dispatcher;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        parseRequest(request, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        parseRequest(request, response);
    }

    protected void parseRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathToWebPage = dispatcher.logicIdentificator(request);

        RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher(pathToWebPage);
        requestDispatcher.forward(request, response);
    }

}
