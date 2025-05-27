package com.suchee.app.utils;

import java.util.UUID;

public class StoragePathBuilder {

    private static final String TEAMS_FOLDER = "teams";
    private static final String ATTACHMENTS_FOLDER = "attachments";
    private static final String AVATARS_FOLDER = "avatars";
    private static final String MEMBERS_FOLDER = "members";
    private static final String BOARDS_FOLDER = "boards";
    private static final String CARDS_FOLDER = "cards";

    /**
     * Path for storing general attachments related directly to a team (not boards/cards).
     * Example: teams/{teamId}/attachments/{uniqueFilename}
     */
    public static String teamAttachmentPath(String teamId, String originalFileName) {
        String uniqueFileName = generateUniqueFileName(originalFileName);
        return String.format("%s/%s/%s/%s", TEAMS_FOLDER, teamId, ATTACHMENTS_FOLDER, uniqueFileName);
    }

    /**
     * Path for storing team avatar
     * Example: teams/{teamId}/avatars/team-avatar.jpg
     */
    public static String teamAvatarPath(String teamId) {
        return String.format("%s/%s/%s/team-avatar.jpg", TEAMS_FOLDER, teamId, AVATARS_FOLDER);
    }

    /**
     * Path for storing member avatar
     * Example: teams/{teamId}/members/{memberId}/avatar.jpg
     */
    public static String memberAvatarPath(String teamId, String memberId) {
        return String.format("%s/%s/%s/%s/avatar.jpg", TEAMS_FOLDER, teamId, MEMBERS_FOLDER, memberId);
    }

    /**
     * Path for storing board wallpaper
     * Example: teams/{teamId}/boards/{boardId}/wallpaper.jpg
     */
    public static String boardWallpaperPath(String teamId, String boardId) {
        return String.format("%s/%s/%s/%s/wallpaper.jpg", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId);
    }

    /**
     * Path for storing attachments inside a board
     * Example: teams/{teamId}/boards/{boardId}/attachments/{uniqueFilename}
     */
    public static String boardAttachmentPath(String teamId, String boardId, String originalFileName) {
        String uniqueFileName = generateUniqueFileName(originalFileName);
        return String.format("%s/%s/%s/%s/%s/%s", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId, ATTACHMENTS_FOLDER, uniqueFileName);
    }

    /**
     * Path for storing attachments inside a card
     * Example: teams/{teamId}/boards/{boardId}/cards/{cardId}/attachments/{uniqueFilename}
     */
    public static String cardAttachmentPath(String teamId, String boardId, String cardId, String originalFileName) {
        String uniqueFileName = generateUniqueFileName(originalFileName);
        return String.format("%s/%s/%s/%s/%s/%s/%s/%s", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId, CARDS_FOLDER, cardId, ATTACHMENTS_FOLDER, uniqueFileName);
    }

    /**
     * Generates a unique file name using a UUID prefix.
     */
    private static String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "-" + originalFileName;
    }
}

