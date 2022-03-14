package intulda.poly.assignment.domain.account.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import intulda.poly.assignment.domain.account.model.Account;
import intulda.poly.assignment.domain.account.model.QAccount;
import intulda.poly.assignment.domain.account.repository.AccountRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.function.Supplier;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public AccountRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Account> findUserById(Account account) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(QAccount.account1)
                .where(idEqual(account.getAccount()))
                .fetchOne());
    }

    private BooleanBuilder idEqual(String account) {
        return nullSafeBuilder(() -> QAccount.account1.account.eq(account));
    }

    private BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }
}
