package intulda.poly.assignment.domain.account.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AccountDTO {

    private String account;
    private String accountPassword;
    private String accountName;


}
