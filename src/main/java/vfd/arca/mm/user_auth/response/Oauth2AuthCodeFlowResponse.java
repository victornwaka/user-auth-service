package vfd.arca.mm.user_auth.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Oauth2AuthCodeFlowResponse {

    private String redirect_uri;


}
