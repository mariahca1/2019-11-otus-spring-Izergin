package ru.otus.spring.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Document("accounts")
public class Account {
    @Field("account_id")
    private Integer accountId;
    @Field("limit")
    private Integer limit;

    private LocalDateTime localDateTime;

    @Override
    public String toString() {
        return String.format("[id=%s,limit=%s]",accountId,limit);
    }
}
