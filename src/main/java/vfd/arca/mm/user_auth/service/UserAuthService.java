package vfd.arca.mm.user_auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import vfd.arca.mm.user_auth.domain.Account;
import vfd.arca.mm.user_auth.domain.User;
import vfd.arca.mm.user_auth.exceptions.UserAuthenticationException;
import vfd.arca.mm.user_auth.exceptions.UserNotFoundException;
import vfd.arca.mm.user_auth.repository.AccountRepo;
import vfd.arca.mm.user_auth.repository.UserRepo;
import vfd.arca.mm.user_auth.request.Oauth2AuthCodeFlowRequest;
import vfd.arca.mm.user_auth.request.UserAuthRequest;
import vfd.arca.mm.user_auth.response.Oauth2AuthCodeFlowResponse;
import vfd.arca.mm.user_auth.response.UserAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UserAuthService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RestTemplate restTemplate;

    public UserAuthResponse authenticateUser(UserAuthRequest userAuthRequest) throws UserNotFoundException,
            UserAuthenticationException {
        logger.debug("username ==>  " + userAuthRequest.getUsername() + "  <=====");

        final User user = userRepo.findByUsername(userAuthRequest.getUsername());
        if (user == null) {
            throw new UserNotFoundException("User not found.");

        } else if (!user.getPassword().equals(userAuthRequest.getPassword())) {
            throw new UserAuthenticationException("Incorrect username or password");

        } else if (user.isFirstLogin()) {
            throw new UserAuthenticationException("Password reset required");

        } else {
            final User fetchedUser = fetchUser(userAuthRequest.getUsername());
            final Account fetchedAccount = fetchAccountId(userAuthRequest.getUsername());
            return new UserAuthResponse(
                    fetchedAccount.getUsername(),
                    fetchedUser.getFirstName(),
                    fetchedUser.getLastName(),
                    fetchedAccount.getAccountId(),
                    fetchedUser.isFirstLogin(),
                    "User authentication was successful.");
        }
    }


    public Oauth2AuthCodeFlowResponse postAuthCodeFlowToKong(Oauth2AuthCodeFlowRequest oauth2AuthCodeFlow)
            throws URISyntaxException {

        URI uri = new URI("https://mifos.arca-infra.com:8333/vfd/api/v1/oauth2/authorize");

        final ResponseEntity<Oauth2AuthCodeFlowResponse> authCodeFlowResponseResponseEntity
                = restTemplate.postForEntity(uri, oauth2AuthCodeFlow, Oauth2AuthCodeFlowResponse.class);

        logger.debug("Status code from kong auth flow =====> "
                + String.valueOf(authCodeFlowResponseResponseEntity.getStatusCodeValue()) + "==================|");

        final Oauth2AuthCodeFlowResponse authCodeFlowResponseResponseEntityBody
                = authCodeFlowResponseResponseEntity.getBody();

        logger.debug("oauth2ImplicitFlowResponseEntityBody =====> " + authCodeFlowResponseResponseEntityBody.toString()
                + " ======================|");
        return authCodeFlowResponseResponseEntityBody;


    }


    private Account fetchAccountId(String username) {
        return accountRepo.findByUsername(username);
    }

    private User fetchUser(String username) {
        return userRepo.findByUsername(username);
    }
}
