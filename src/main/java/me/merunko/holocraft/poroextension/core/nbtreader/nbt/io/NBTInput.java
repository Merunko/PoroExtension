package me.merunko.holocraft.poroextension.core.nbtreader.nbt.io;

import me.merunko.holocraft.poroextension.core.nbtreader.nbt.tag.Tag;

import java.io.IOException;

public interface NBTInput {

    NamedTag readTag(int maxDepth) throws IOException;

    Tag<?> readRawTag(int maxDepth) throws IOException;
}
