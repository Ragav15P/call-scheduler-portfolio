package in.ragav.personalportfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import in.ragav.personalportfolio.model.CallScheduler;
import in.ragav.personalportfolio.repo.CallSchedulerInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class CallSchedulerServiceImplementation implements CallSchedulerService {

    @Autowired
    private CallSchedulerInterface callSchedulerRepo;

    @Autowired
    private JavaMailSender mailSender;

    
    @Override
    public void scheduleCall(CallScheduler callScheduler) throws MessagingException {
        // Save call schedule info to DB
        callSchedulerRepo.save(callScheduler); 

        // Prepare HTML email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(callScheduler.getEmail());
        helper.setSubject("✅ 1:1 Call Scheduled Successfully");

        String content = "<div style='font-family: Arial, sans-serif; font-size: 14px;'>"
                + "<h2 style='color: #2E86C1;'>Hi " + callScheduler.getUserName() + ",</h2>"
                + "<p>🎉 Thank you for scheduling a <strong>1:1 call</strong> with me!</p>"
                + "<p><strong>📅 Scheduled Date & Time:</strong><br/>"
                + callScheduler.getPreferredDateTime().toString() + "</p>"
                + "<p><strong>💬 Your Message:</strong><br/>"
                + (callScheduler.getMessage() != null && !callScheduler.getMessage().isEmpty() ? callScheduler.getMessage() : "<i>No message provided.</i>") + "</p>"
                + "<p>We’ll connect with you at your selected time.</p>"
                + "<br/><p style='color: gray;'>Warm regards,<br/>"
                + "Ragav<br/>Personal Portfolio Team</p>"
                + "<hr style='border: none; border-top: 1px solid #ddd;'>"
                + "<p style='font-size: 12px; color: #888;'>This is an automated confirmation email.</p>"
                + "</div>";

        helper.setText(content, true); // true means this is HTML content

        mailSender.send(message);
    }

}
