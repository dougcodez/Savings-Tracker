package dev.savingstracker.session;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SessionIDManager {

    public String generateNew() {
        SessionIDGenerator sessionIDGenerator = new SessionIDGenerator.SessionIDGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return sessionIDGenerator.generate();
    }
}
