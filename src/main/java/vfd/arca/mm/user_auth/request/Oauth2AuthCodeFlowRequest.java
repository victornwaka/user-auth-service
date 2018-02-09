package vfd.arca.mm.user_auth.request;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Oauth2AuthCodeFlowRequest {

    private String response_type;
    private String scope;
    private String authenticated_userid;
    private String client_id;
    private String provision_key;

}
