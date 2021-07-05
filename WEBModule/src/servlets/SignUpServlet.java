package servlets;

import enginePackage.User;
import enginePackage.Users;
import utils.ServletsUtils;
import utils.SessionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SignUpServlet extends HttpServlet
{
    /******************************************************************************/
    private String LOG_IN_URL = "../logIn/logIn.html";
    private String RSE_HOME_PAGE_URL =  "../homePage/brokerHomePage.html";
    /******************************************************************************/
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType("text/html;charst=UTF-8");
        String userNameFromSession = SessionUtils.getUsername(request);
        Users users = ServletsUtils.getUsers(getServletContext());
        PrintWriter out = response.getWriter();

        if(userNameFromSession != null) // User already have session -> go to login
            response.sendRedirect(LOG_IN_URL);
        else
        {
            String userNameFromParameter = request.getParameter("username");
            String userType = request.getParameter("type");
            if(userNameFromParameter == null || userNameFromParameter.isEmpty() || userType == null || userType.isEmpty() )
            {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Missing information!');");
                out.println("</script>");
            }
            else // all information is filled.
            {
                userNameFromParameter = userNameFromParameter.trim();
                synchronized (this)
                {
                    if(users.isUserExist(userNameFromParameter)) // Users name is already taken.
                    {
                        String error = "Username " + userNameFromParameter + " is already taken. Choose a different name.";
                        out.println(error);
                        response.setStatus(400);
                    }
                    else
                    {
                        users.addUser(userNameFromParameter, User.Type.valueOf(userType));
                        request.getSession(true).setAttribute("username", userNameFromParameter);
                        response.sendRedirect(RSE_HOME_PAGE_URL);
                    }
                }
            }
        }
    }
    /******************************************************************************/
}
