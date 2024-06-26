package me.merunko.holocraft.poroextension.core.nbtreader.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

public interface Deserializer<T> {

    T fromStream(InputStream stream) throws IOException;

    default T fromFile(File file) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            return fromStream(bis);
        }
    }

    default T fromBytes(byte[] data) throws IOException {
        ByteArrayInputStream stream = new ByteArrayInputStream(data);
        return fromStream(stream);
    }

    default T fromResource(Class<?> clazz, String path) throws IOException {
        try (InputStream stream = clazz.getClassLoader().getResourceAsStream(path)) {
            if (stream == null) {
                throw new IOException("resource \"" + path + "\" not found");
            }
            return fromStream(stream);
        }
    }

    default T fromURL(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            return fromStream(stream);
        }
    }

}
