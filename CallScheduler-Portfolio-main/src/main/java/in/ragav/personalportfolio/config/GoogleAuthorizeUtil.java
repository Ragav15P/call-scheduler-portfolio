package in.ragav.personalportfolio.config;

import org.springframework.stereotype.Component;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;

import java.io.IOException;
import java.util.List;

@Component
public class GoogleAuthorizeUtil {

    private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
    private static final String REDIRECT_URI = System.getenv("GOOGLE_REDIRECT_URI");

    public AuthorizationCodeFlow getFlow() throws IOException {
        return new AuthorizationCodeFlow.Builder(
                BearerToken.authorizationHeaderAccessMethod(),
                new NetHttpTransport(),
                JacksonFactory.getDefaultInstance(),
                new GenericUrl("https://oauth2.googleapis.com/token"),
                new ClientParametersAuthentication(CLIENT_ID, CLIENT_SECRET),
                CLIENT_ID,
                "https://accounts.google.com/o/oauth2/auth")
            .setScopes(List.of(CalendarScopes.CALENDAR))
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
            .build();
    }

    public String getRedirectUri() {
        return REDIRECT_URI;
    }
}
