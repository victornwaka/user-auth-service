package vfd.arca.mm.user_auth.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserAuthRequest {
    private String username;
    private String password;
}
