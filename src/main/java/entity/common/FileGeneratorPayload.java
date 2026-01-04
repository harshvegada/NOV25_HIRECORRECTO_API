package entity.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Unified file generator payload for both Audio and Video file uploads
 */
@Builder
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileGeneratorPayload {
    private String originalFilename;
    private String fileType;
    private String mimeType;

    public static FileGeneratorPayload forAudio() {
        return FileGeneratorPayload.builder()
                .originalFilename("recordedAudio.webm")
                .fileType("audio")
                .mimeType("audio/webm")
                .build();
    }

    public static FileGeneratorPayload forVideo() {
        return FileGeneratorPayload.builder()
                .originalFilename("recordedVideo.webm")
                .fileType("video")
                .mimeType("video/webm")
                .build();
    }
}

