package intulda.poly.assignment.domain.account.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.QAccount;
import intulda.poly.assignment.domain.account.repository.AccountRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AccountRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Account> findUser(Account account) {
        Account user = jpaQueryFactory.selectFrom(QAccount.account1)
                .where(QAccount.account1.account.eq(account.getAccount())
                        .and(QAccount.account1.password.eq(account.getPassword())))
                .fetchOne();
        return Optional.ofNullable(user);
    }
}
