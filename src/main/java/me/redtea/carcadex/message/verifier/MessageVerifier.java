package me.redtea.carcadex.message.verifier;

import java.util.Optional;

public interface MessageVerifier {
    Optional<String> fromDefault(String key);
}
