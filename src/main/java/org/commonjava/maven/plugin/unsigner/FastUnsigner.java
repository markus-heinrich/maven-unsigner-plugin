package org.commonjava.maven.plugin.unsigner;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.AbstractFileHeader;
import net.lingala.zip4j.model.FileHeader;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FastUnsigner implements Unsigner {
    public File unsign(final File src, Log log) {
        try(ZipFile zipFile = new ZipFile(src)) {
            List<FileHeader> fileHeaders = zipFile.getFileHeaders();
            List<String> filesToRemove = fileHeaders.stream()
                    .filter(h -> h.getFileName().endsWith(".SF") || h.getFileName().endsWith(".RSA") || h.getFileName().endsWith(".LIST"))
                    .map(AbstractFileHeader::getFileName)
                    .collect(Collectors.toList());
            zipFile.removeFiles(filesToRemove);
            return src;
        } catch (IOException e) {
            log.error("Exception while processing zip file", e);
            throw new RuntimeException(e);
        }
    }
}
