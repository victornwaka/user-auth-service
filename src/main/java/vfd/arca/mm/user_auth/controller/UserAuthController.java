package vfd.arca.mm.user_auth.controller;

import vfd.arca.mm.user_auth.exceptions.UserAuthenticationException;
import vfd.arca.mm.user_auth.request.Oauth2AuthCodeFlowRequest;
import vfd.arca.mm.user_auth.request.UserAuthRequest;
import vfd.arca.mm.user_auth.response.Oauth2AuthCodeFlowResponse;
import vfd.arca.mm.user_auth.response.UserAuthResponse;
import vfd.arca.mm.user_auth.service.UserAuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vfd.arca.mm.user_auth.response.Error;
import vfd.arca.mm.user_auth.exceptions.UserNotFoundException;

@RestController
public class UserAuthController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserAuthService userAuthService;


    @CrossOrigin
    @PostMapping("/v1/auth")
    @ResponseBody
    @ApiOperation(value = "authUser", notes = "User authentication")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = UserAuthResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public UserAuthResponse authenticateUser(@RequestBody UserAuthRequest userAuthRequest)
            throws UserNotFoundException, UserAuthenticationException {
        logger.debug("User auth payload ==> " + userAuthRequest + " <==== ");
        return userAuthService.authenticateUser(userAuthRequest);
    }

    @CrossOrigin
    @PostMapping(value = "/v1/oauth2/authorize", consumes = "application/json", produces = "text/html")
    @ResponseBody
    @ApiOperation(value = "oauth2Authorization", notes = "oauth2 Implicit flow")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = UserAuthResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Error.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = Error.class)
    })
    public String oauth2Authorization(@RequestBody Oauth2AuthCodeFlowRequest oauth2ImplicitFlow)
        /*throws UserNotFoundException, UserAuthenticationException, URISyntaxException */ {
        logger.debug("oauth2Authorization flow ==> " + oauth2ImplicitFlow + " <==== ");

        try {
            final Oauth2AuthCodeFlowResponse oauth2ImplicitFlowResponse
                    = userAuthService.postAuthCodeFlowToKong(oauth2ImplicitFlow);

            logger.debug("redirect_uri =====> " + oauth2ImplicitFlowResponse.getRedirect_uri() + " ==============================}");

            return oauth2ImplicitFlowResponse.getRedirect_uri();
//            return new RedirectView(oauth2ImplicitFlowResponse.getRedirect_uri());
        } catch (Exception e) {
            logger.debug("Error =====> " + e.getMessage() + "  <=========== |");
            e.printStackTrace();
        }

        return null;
    }

}
