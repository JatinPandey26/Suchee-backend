package com.suchee.app.utils;

import com.suchee.app.dto.AttachmentStorageInfoDto;
import com.suchee.app.enums.AttachmentType;
import com.suchee.app.service.impl.CloudinaryServiceImpl;

import java.util.UUID;

public class StorageInfoBuilder {

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
    public static AttachmentStorageInfoDto teamAttachmentPath(String teamId, String originalFileName) {
        return buildDto(String.format("%s/%s/%s", TEAMS_FOLDER, teamId, ATTACHMENTS_FOLDER), originalFileName);
    }

    /**
     * Path for storing attachments inside a board
     * Example: teams/{teamId}/boards/{boardId}/attachments/{uniqueFilename}
     */
    public static AttachmentStorageInfoDto boardAttachmentPath(String teamId, String boardId, String originalFileName) {
        return buildDto(String.format("%s/%s/%s/%s/%s", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId, ATTACHMENTS_FOLDER), originalFileName);
    }

    /**
     * Path for storing attachments inside a card
     * Example: teams/{teamId}/boards/{boardId}/cards/{cardId}/attachments/{uniqueFilename}
     */
    public static AttachmentStorageInfoDto cardAttachmentPath(String teamId, String boardId, String cardId, String originalFileName) {
        return buildDto(String.format("%s/%s/%s/%s/%s/%s/%s", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId, CARDS_FOLDER, cardId, ATTACHMENTS_FOLDER), originalFileName);
    }

    private static AttachmentStorageInfoDto buildDto(String baseFolderPath, String originalFileName) {
        String uniqueFileName = generateUniqueFileName(originalFileName);
        String fullPath = UUID.randomUUID().toString();

        AttachmentStorageInfoDto dto = new AttachmentStorageInfoDto();
        dto.setFolderPath(baseFolderPath);
        dto.setPublicId(fullPath); // publicId is same as full cloud path in Cloudinary
        return dto;
    }

    private static String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID() + "-" + originalFileName;
    }

    // Avatar & Wallpaper methods stay unchanged as they don't need a DTO
    public static String teamAvatarPath(String teamId) {
        return String.format("%s/%s/%s/team-avatar.jpg", TEAMS_FOLDER, teamId, AVATARS_FOLDER);
    }

    public static String memberAvatarPath(String teamId, String memberId) {
        return String.format("%s/%s/%s/%s/avatar.jpg", TEAMS_FOLDER, teamId, MEMBERS_FOLDER, memberId);
    }

    public static String boardWallpaperPath(String teamId, String boardId) {
        return String.format("%s/%s/%s/%s/wallpaper.jpg", TEAMS_FOLDER, teamId, BOARDS_FOLDER, boardId);
    }

    public static String UserAvatarPath(String email){
        return String.format("%s/avatar.jpg", email);
    }

    public static String getFolderPathByAttachmentType(String type,String... pathVars){
        if(type.equalsIgnoreCase(AttachmentType.USER_AVATAR.name())){
            return UserAvatarPath(pathVars[0]);
        }

        return "/bin";
    }

}


