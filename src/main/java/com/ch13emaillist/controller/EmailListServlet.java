package com.ch13emaillist.controller;

import java.io.IOException;

import com.ch13emaillist.model.User;
import com.ch13emaillist.model.UserDB;
import com.ch13emaillist.util.MailUtilGmail;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Must be set before reading request parameters
        request.setCharacterEncoding("UTF-8");

        String url = "/index.jsp";
        String message = "";

        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if ("add".equals(action)) {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            User user = new User(firstName, lastName, email);
            request.setAttribute("user", user);

            if (firstName == null || firstName.isBlank()
                    || lastName == null || lastName.isBlank()
                    || email == null || email.isBlank()) {

                message = "Please fill in all required fields.";

            } else if (UserDB.emailExists(email)) {

                message = "This email address already exists. "
                        + "Please enter another email address.";

            } else {
                // Save the user before sending the welcome email
                UserDB.insert(user);
                url = "/thanks.jsp";

                String to = email;

                // MailUtilGmail uses its configured GMAIL_USERNAME
                String from = null;

                String subject = "Welcome to our email list";

                String body = "Dear " + firstName + ",\n\n"
                        + "Thanks for joining our email list. "
                        + "We'll make sure to send "
                        + "you announcements about new products "
                        + "and promotions.\n"
                        + "Have a great day and thanks again!\n\n"
                        + "Nguyen Tuan Duy";

                try {
                    MailUtilGmail.sendMail(
                            to, from, subject, body, false
                    );
                } catch (MessagingException e) {
                    request.setAttribute(
                            "errorMessage",
                            "Your registration was saved, but we "
                                    + "could not send the confirmation email."
                    );

                    log("Unable to send the welcome email.", e);
                }
            }
        }

        request.setAttribute("message", message);

        request.getRequestDispatcher(url)
                .forward(request, response);
    }
}