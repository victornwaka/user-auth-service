package vfd.arca.mm.user_auth.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "username")
    private String username;
    @Column(name = "created_date")
    private String createdDate;
}