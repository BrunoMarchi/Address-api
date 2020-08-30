package com.example.address;

import com.example.address.model.AddressModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {

    public static final Long DEFAULT_ID = 12345L;

    public static AddressModel getDefaultTestProduct() {
        return AddressModel
                .builder()
                .streetName("Wall Street")
                .number(123)
                .complement(":)")
                .neighbourhood("My neighbourhood")
                .city("São Paulo")
                .state("SP")
                .country("Brazil")
                .zipcode("13500-00")
                .latitude("123188123")
                .longitude("987645345")
                .build();
    }
}
