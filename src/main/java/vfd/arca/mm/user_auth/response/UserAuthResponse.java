package vfd.arca.mm.user_auth.response;

import vfd.arca.mm.user_auth.domain.User;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAuthResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String accountId;
//    private boolean isAuthenticated;
//    private String createdDate;
    private boolean isFirstLogin;
    private String userAuthMessage;
}
