package me.merunko.holocraft.poroextension.core.nbtreader.nbt.io;

import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.Tag;

import java.io.IOException;

public interface NBTOutput {

    void writeTag(NamedTag tag, int maxDepth) throws IOException;

    void writeTag(Tag<?> tag, int maxDepth) throws IOException;

    void flush() throws IOException;
}
