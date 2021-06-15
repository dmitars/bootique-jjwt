package io.bootique.jjwt;

import io.bootique.di.Injector;
import io.bootique.di.TypeLiteral;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Deserializer;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class ParserBehaviourResolver {
    private final Injector injector;

    public ParserBehaviourResolver(Injector injector) {
        Objects.requireNonNull(injector, "Injector cannot be null!");
        this.injector = injector;
    }


    public Optional<SigningKeyResolver> keyResolver() {
        return injector.getInstance(io.bootique.di.Key.getOptionalOf(SigningKeyResolver.class));
    }

    public Optional<CompressionCodecResolver> compressionCodecResolver() {
        return injector.getInstance(io.bootique.di.Key.getOptionalOf(CompressionCodecResolver.class));
    }

    public Optional<Decoder<String, byte[]>> decoder() {
        return injector.getInstance(io.bootique.di.Key.getOptionalOf(
                io.bootique.di.Key.get(new TypeLiteral<Decoder<String, byte[]>>(){})
        ));
    }

    public Optional<Deserializer<Map<String, ?>>> deserializer() {
        return injector.getInstance(io.bootique.di.Key.getOptionalOf(
                io.bootique.di.Key.get(new TypeLiteral<Deserializer<Map<String, ?>>>(){})
        ));
    }
}
