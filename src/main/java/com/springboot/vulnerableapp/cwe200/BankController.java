package com.springboot.vulnerableapp.cwe200;

import com.springboot.vulnerableapp.hello.Message;
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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class BankController {
    @RequestMapping("/bank")
    public BankAccount getUserBankAccount(@RequestParam(value="userName") String userName,
                                          @RequestParam(value="accountNumber") String accountNumber) {
        BankAccount userAccount = null;
        String query = null;
        try {
            if (isAuthorizedUser(userName)) {
                query = "SELECT * FROM accounts WHERE owner = "
                        + userName + " AND accountID = " + accountNumber;
                DatabaseManager dbManager = new DatabaseManager();
                Connection conn = dbManager.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet queryResult = stmt.executeQuery(query);
                userAccount = (BankAccount)queryResult.getObject(accountNumber);
            }
        } catch (Exception ex) {
            String logMessage = "Unable to retrieve account information from database,\nquery: " + query;
            Logger.getLogger(BankAccount.class.getName()).log(Level.SEVERE, logMessage, ex);
        }
        return userAccount;
    }

    @RequestMapping("/getGet")
    protected void getUserUrl(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            String userId = request.getParameter("userId");
            userId = ESAPI.encoder().encodeForSQL(new MySQLCodec(MySQLCodec.Mode.STANDARD), userId);
            DatabaseManager dbManager = new DatabaseManager();
            Connection conn = dbManager.getConnection();
            Statement st = conn.createStatement();
            String query = "SELECT * FROM User WHERE userId='" + userId + "';";
            ResultSet res = st.executeQuery(query);
            String url = "https://" +userId+ ".company.com";
            response.sendRedirect(url);
        }
        catch (Exception e)
        {
            Logger.getLogger(e.getMessage());
        }
    }

    private boolean isAuthorizedUser(String userName) {
        //for testing purposes
        return true;
    }
}
