package me.merunko.holocraft.poroextension.core.nbtreader.nbt.io;

import me.merunko.holocraft.poroextension.core.nbtreader.io.StringSerializer;
import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.Tag;

import java.io.IOException;
import java.io.Writer;

public class SNBTSerializer implements StringSerializer<Tag<?>> {

    @Override
    public void toWriter(Tag<?> tag, Writer writer) throws IOException {
        SNBTWriter.write(tag, writer);
    }

    public void toWriter(Tag<?> tag, Writer writer, int maxDepth) throws IOException {
        SNBTWriter.write(tag, writer, maxDepth);
    }
}
