package com.fasterxml.jackson.datatype.eclipsecollections.ser.map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.datatype.primitive_collections_base.ser.map.PrimitiveMapSerializer;
import org.eclipse.collections.api.PrimitiveIterable;
import org.eclipse.collections.api.map.primitive.*;

import static com.fasterxml.jackson.datatype.eclipsecollections.ser.map.PrimitiveRefMapSerializers.rethrowUnchecked;

/**
 * @author yawkat
 */
public final class PrimitivePrimitiveMapSerializers {
    private PrimitivePrimitiveMapSerializers() {
    }

    public static Map<Class<? extends PrimitiveIterable>, PrimitiveMapSerializer<?>> getInstances() {
        return INSTANCES;
    }

    private static final Map<Class<? extends PrimitiveIterable>, PrimitiveMapSerializer<?>> INSTANCES;

    /* with
        byte|char|short|int|long|float|double key
        short|byte|char|int|long|float|double|boolean value
    */
    private static final PrimitiveMapSerializer<ByteShortMap> BYTE_SHORT =
            new PrimitiveMapSerializer<ByteShortMap>(ByteShortMap.class) {

                @SuppressWarnings("RedundantThrows") // May throw IOException from inside lambda
                @Override
                protected void serializeEntries(ByteShortMap value, JsonGenerator gen, SerializerProvider serializers)
                        throws IOException {
                    value.forEachKeyValue((k, v) -> {
                        try {
                            gen.writeFieldName(String.valueOf(k));
                            /* if !(char|boolean value) */
                            gen.writeNumber(v);
                            /* elif char value //
                            gen.writeString(new char[]{v}, 0, 1);
                            /* elif boolean value //
                            gen.writeBoolean(v);
                            // endif */
                        } catch (IOException e) {
                            rethrowUnchecked(e);
                        }
                    });
                }

                @Override
                public boolean isEmpty(SerializerProvider provider, ByteShortMap value) {
                    return value.isEmpty();
                }
            };
    /* endwith */

    static {
        Map<Class<? extends PrimitiveIterable>, PrimitiveMapSerializer<?>> instances =
                new HashMap<>();
        /* with
            byte|char|short|int|long|float|double key
            short|byte|char|int|long|float|double|boolean value
        */
        instances.put(ByteShortMap.class, BYTE_SHORT);
        /* endwith */
        INSTANCES = Collections.unmodifiableMap(instances);
    }
}
