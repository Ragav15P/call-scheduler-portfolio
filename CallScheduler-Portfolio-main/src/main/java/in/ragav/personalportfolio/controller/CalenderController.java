package in.ragav.personalportfolio.controller;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.*;

import in.ragav.personalportfolio.config.GoogleAuthorizeUtil;
import in.ragav.personalportfolio.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.util.Arrays;

@Controller
public class CalenderController {

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private GoogleAuthorizeUtil gle;
    
    

    @GetMapping("/request-meeting")
    public String requestMeeting(
            @RequestParam String userName,
            @RequestParam String email,
            @RequestParam String preferredDateTime,
            @RequestParam(required = false) String message,
            HttpSession session) throws Exception {

        // Store data in session
        session.setAttribute("userName", userName);
        session.setAttribute("email", email);
        session.setAttribute("preferredDateTime", preferredDateTime);
        session.setAttribute("message", message);

        // Start OAuth flow
        AuthorizationCodeFlow flow = gle.getFlow();
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl()
                .setRedirectUri("http://localhost:9090/oauth2callback");

        return "redirect:" + authorizationUrl.build();
    }

    @GetMapping("/oauth2callback")
    public String oauth2Callback(
            @RequestParam("code") String code,
            HttpSession session,
            Model model) throws Exception {

        // Complete OAuth
        AuthorizationCodeFlow flow = gle.getFlow();
        TokenResponse response = flow.newTokenRequest(code)
                .setRedirectUri("http://localhost:9090/oauth2callback")
                .execute();

        Credential credential = flow.createAndStoreCredential(response, "user");

        Calendar service = new Calendar.Builder(
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("1:1 Call Scheduler")
                .build();

        // Retrieve session data
        String userName = (String) session.getAttribute("userName");
        String email = (String) session.getAttribute("email");
        String preferredDateTime = (String) session.getAttribute("preferredDateTime");
        String message = (String) session.getAttribute("message");

        // Fix datetime if missing seconds
        if (preferredDateTime.length() == 16) {
            preferredDateTime += ":00"; // Ensure it's in RFC 3339 format
        }

        // Create event
        Event event = new Event()
                .setSummary("1:1 Call with " + userName)
                .setDescription("Message: " + (message != null ? message : "No message") + "\nEmail: " + email);

        DateTime startDateTime = new DateTime(preferredDateTime);
        EventDateTime start = new EventDateTime().setDateTime(startDateTime).setTimeZone("Asia/Kolkata");

        long endMillis = startDateTime.getValue() + 30 * 60 * 1000; // Add 30 minutes
        DateTime endDateTime = new DateTime(endMillis);
        EventDateTime end = new EventDateTime().setDateTime(endDateTime).setTimeZone("Asia/Kolkata");

        event.setStart(start);
        event.setEnd(end);

        // Add Google Meet link
        ConferenceData conferenceData = new ConferenceData();
        CreateConferenceRequest createConferenceRequest = new CreateConferenceRequest()
                .setRequestId("meet-" + System.currentTimeMillis());
        conferenceData.setCreateRequest(createConferenceRequest);
        event.setConferenceData(conferenceData);

        // Add attendees
        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail(email)
        };
        event.setAttendees(Arrays.asList(attendees));

        // Insert event
        Event createdEvent = service.events().insert("primary", event)
                .setConferenceDataVersion(1)
                .execute();

        String joinLink = createdEvent.getHangoutLink();

        // Email confirmation to user
        String subject = "Your 1:1 Call with Ragav is Scheduled!";
        String content = "Hi " + userName + ",\n\nYour 1:1 call is scheduled at: " +
                preferredDateTime + " IST.\n\nGoogle Meet Link: " + joinLink +
                "\n\nMessage: " + (message != null ? message : "No message") +
                "\n\nRegards,\nRagav";
        emailService.sendEmail(email, subject, content);

        // Email notification to admin
        String adminSubject = "New 1:1 Call Scheduled";
        String adminContent = "A new meeting has been scheduled by: " + userName +
                "\nEmail: " + email +
                "\nTime: " + preferredDateTime +
                "\nMessage: " + (message != null ? message : "No message") +
                "\nGoogle Meet: " + joinLink;
        emailService.sendEmail("rag18av@gmail.com", adminSubject, adminContent);

        // Show success page or redirect
        model.addAttribute("userName", userName);
        model.addAttribute("meetingLink", joinLink);
        return "success"; // should map to success.html or Thymeleaf template
    }
}