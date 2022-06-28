package dev.savingstracker.session;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SessionIDGenerator {

        private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final String DIGITS = "0123456789";
        private static final String SPECIAL = "!@#$%&*()_+-=[]|,./?><";
        private boolean useLower;
        private boolean useUpper;
        private boolean useDigits;
        private boolean useSpecial;

        private SessionIDGenerator() {
            throw new UnsupportedOperationException("Empty constructor is never supported");
        }

        private SessionIDGenerator(SessionIDGeneratorBuilder builder) {
            this.useLower = builder.useLower;
            this.useUpper = builder.useUpper;
            this.useDigits = builder.useDigits;
            this.useSpecial = builder.useSpecial;
        }

        public static class SessionIDGeneratorBuilder {
            private boolean useLower;
            private boolean useUpper;
            private boolean useDigits;
            private boolean useSpecial;

            public SessionIDGeneratorBuilder() {
                this.useLower = false;
                this.useUpper = false;
                this.useDigits = false;
                this.useSpecial = false;
            }

            public SessionIDGeneratorBuilder useLower(boolean useLower) {
                this.useLower = useLower;
                return this;
            }

            public SessionIDGeneratorBuilder useUpper(boolean useUpper) {
                this.useUpper = useUpper;
                return this;
            }

            public SessionIDGeneratorBuilder useDigits(boolean useDigits) {
                this.useDigits = useDigits;
                return this;
            }

            public SessionIDGeneratorBuilder useSpecial(boolean useSpecial) {
                this.useSpecial = useSpecial;
                return this;
            }

            public SessionIDGenerator build() {
                return new SessionIDGenerator(this);
            }
        }

        public String generate() {
            int length = 16;
            StringBuilder sessionID = new StringBuilder();
            ThreadLocalRandom random = ThreadLocalRandom.current();
            List<String> charCategories = Collections.synchronizedList(new LinkedList<>());
            if (useLower) {
                charCategories.add(LOWERCASE);
            }

            if (useUpper) {
                charCategories.add(UPPERCASE);
            }

            if (useDigits) {
                charCategories.add(DIGITS);
            }

            if (useSpecial) {
                charCategories.add(SPECIAL);
            }

            for (int i = 0; i < length; i++) {
                String charCategory = charCategories.get(random.nextInt(charCategories.size()));
                int position = random.nextInt(charCategory.length());
                sessionID.append(charCategory.charAt(position));
            }

            return new String(sessionID);
        }
    }

