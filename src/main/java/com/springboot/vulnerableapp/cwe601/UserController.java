package com.springboot.vulnerableapp.cwe601;

import com.springboot.vulnerableapp.cwe200.BankAccount;
import com.springboot.vulnerableapp.cwe200.DatabaseManager;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.codecs.MySQLCodec;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {

    @RequestMapping("/getUserUrl")
    protected void getUserUrl(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException
    {
        String userId = request.getParameter("userId");
        String url = "https://www.test.com/" + userId;
        response.sendRedirect(url);
    }

    //@RequestMapping("/getUserUrl")
    protected void getUrl(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String userId = request.getParameter("userId");
            userId = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), userId);
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();
            //Statement st = conn.createStatement();
            //String query = "SELECT * FROM User WHERE userId='" + userId + "';";
            //ResultSet res = st.executeQuery(query);
            String url = "https://" +userId+ ".company.com";
            response.sendRedirect(url);
        }
        catch (Exception e)
        {
            Logger.getLogger(e.getMessage());
        }
    }
}
