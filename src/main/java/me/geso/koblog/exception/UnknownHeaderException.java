package me.geso.koblog.exception;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class UnknownHeaderException extends Exception {
    private final Path filePath;
    private final String headerName;

    public UnknownHeaderException(Path filePath, String headerName) {
        super("Unknown header field '" + headerName + "' in '" + filePath + "'");
        this.filePath = filePath;
        this.headerName = headerName;
    }
}
