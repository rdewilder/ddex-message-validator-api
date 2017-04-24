package net.ddex.ern.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by rdewilde on 4/16/2017.
 */
public class ValidatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Test!!!");
        PrintWriter out = resp.getWriter();
        out.println("this is a sample");
        out.flush();

    }
}
