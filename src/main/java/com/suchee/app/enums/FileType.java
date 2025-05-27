package com.suchee.app.enums;

import java.util.List;
import java.util.Map;

/**
 * Enum representing different types of files supported by the application.
 *
 * <p>Each {@code FileType} has a human-readable {@code displayName} and a list
 * of associated file extensions defined in the {@code SUPPORTED_EXTENSIONS} map.</p>
 *
 * <p>This enum provides a utility method {@code fromExtension(String)} to determine
 * the {@code FileType} from a given file extension.</p>
 */
public enum FileType {

    IMAGE("Image"),
    VIDEO("Video"),
    AUDIO("Audio"),
    DOCUMENT("Document"),
    SPREADSHEET("Spreadsheet"),
    PRESENTATION("Presentation"),
    ARCHIVE("Archive"),
    CODE("Code"),
    OTHER("Other");

    private final String displayName;

    FileType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the display name of the file type.
     *
     * @return the human-readable display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * A map of supported extensions for each {@code FileType}.
     */
    public static final Map<FileType, List<String>> SUPPORTED_EXTENSIONS = Map.of(
            IMAGE, List.of("png", "jpg", "jpeg", "gif", "bmp", "webp"),
            VIDEO, List.of("mp4", "mov", "avi", "mkv", "webm"),
            AUDIO, List.of("mp3", "wav", "aac", "ogg"),
            DOCUMENT, List.of("pdf", "doc", "docx", "txt", "rtf", "odt"),
            SPREADSHEET, List.of("xls", "xlsx", "csv", "ods"),
            PRESENTATION, List.of("ppt", "pptx", "odp"),
            ARCHIVE, List.of("zip", "rar", "tar", "gz", "7z"),
            CODE, List.of("js", "java", "py", "ts", "html", "css")
    );

    /**
     * Determines the {@code FileType} based on a file extension.
     *
     * @param extension the file extension (case-insensitive)
     * @return the corresponding {@code FileType}, or {@code OTHER} if not matched
     */
    public static FileType fromExtension(String extension) {
        String ext = extension.toLowerCase();
        return SUPPORTED_EXTENSIONS.entrySet().stream()
                .filter(e -> e.getValue().contains(ext))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(OTHER);
    }
}
