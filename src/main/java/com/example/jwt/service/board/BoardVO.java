package com.example.jwt.service.board;

import com.example.jwt.service.user.UserEntity;
import com.example.jwt.service.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardVO {

    private String UID;
    private String name;
    private String creatorUID;
    private String creatorName;
    private String content;
    private int good;
    private boolean isNotice;
    private LocalDate createdAt;

    public static BoardVO toBoardVO(BoardEntity entity) {
        return BoardVO.builder()
                .UID(entity.getUID())
                .name(entity.getName())
                .creatorUID(entity.getCreatorUID())
                .creatorName(entity.getCreator().getName())
                .content(entity.getContent())
                .good(entity.getGood())
                .isNotice(entity.isNotice())
                .createdAt(entity.getCreatedAt().toLocalDate())
                .build();
    }
}
