package io.bootique.jjwt;

import io.bootique.ModuleExtender;
import io.bootique.di.Binder;
import io.bootique.di.Key;
import io.bootique.di.TypeLiteral;
import io.bootique.type.TypeRef;
import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Deserializer;

import java.lang.reflect.Type;
import java.util.Map;

public class JJwtModuleExtender extends ModuleExtender<JJwtModuleExtender> {
    public JJwtModuleExtender(Binder binder) {
        super(binder);
    }

    @Override
    public JJwtModuleExtender initAllExtensions() {
       initOptionals();
        return this;
    }

    private void initOptionals(){
        binder.bindOptional(SigningKeyResolver.class);
        TypeRef<Deserializer<Map<String, ?>>> typeRef = new TypeRef<Deserializer<Map<String, ?>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        binder.bindOptional(Key.get(TypeLiteral.of(typeRef.getType())));
        binder.bindOptional(CompressionCodecResolver.class);
        TypeRef<Decoder<String,byte[]>> decoderTypeRef = new TypeRef<Decoder<String,byte[]>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        binder.bindOptional(Key.get(TypeLiteral.of(decoderTypeRef.getType())));
    }

    public JJwtModuleExtender setSigningKeyResolver(SigningKeyResolver signingKeyResolver) {
        binder.bind(SigningKeyResolver.class).toInstance(signingKeyResolver);
        return this;
    }

    public JJwtModuleExtender setCompressionCodecResolver(CompressionCodecResolver compressionCodecResolver) {
        binder.bindOptional(CompressionCodecResolver.class).toInstance(compressionCodecResolver);
        return this;
    }

    public JJwtModuleExtender setDeserializer(Deserializer<Map<String, ?>> deserializer) {
        TypeRef<Deserializer<Map<String, ?>>> typeRef = new TypeRef<Deserializer<Map<String, ?>>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        binder.bindOptional(Key.get(TypeLiteral.of(typeRef.getType()))).toInstance(deserializer);
        return this;
    }

    public JJwtModuleExtender setDecoder(Decoder<String, byte[]> decoder) {
        TypeRef<Decoder<String,byte[]>> typeRef = new TypeRef<Decoder<String,byte[]>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        };
        binder.bindOptional(Key.get(TypeLiteral.of(typeRef.getType()))).toInstance(decoder);
        return this;
    }
}
