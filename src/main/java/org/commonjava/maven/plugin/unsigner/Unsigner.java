package org.commonjava.maven.plugin.unsigner;

import org.apache.maven.plugin.logging.Log;

import java.io.File;

public interface Unsigner {
    File unsign(final File src, Log log);
}
