package at.fhburgenland;

import org.springframework.stereotype.Service;

// FIXME: add javadoc
@Service
public class StringService {
    // FIXME: add javadoc
    public String toUpperCase(final String string) {
        if (string == null) {
            return null;
        }
        return string.toUpperCase();
    }
}
