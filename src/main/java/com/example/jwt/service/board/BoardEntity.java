package com.example.jwt.service.board;

import com.example.jwt.config.base.BaseEntity;
import com.example.jwt.config.constant.EntitiyConstant;
import com.example.jwt.service.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(
        name = "board"
)
@NoArgsConstructor
@AllArgsConstructor
public class BoardEntity extends BaseEntity {

    String name;

    @Column(name = "creator_uid")
    String creatorUID;

    @Column(name = "full_path_index")
    String fullPathIndex;

    @ManyToOne(fetch = FetchType.LAZY) // ✅ 연관관계 매핑
    @JoinColumn(name = "creator_uid", referencedColumnName = "uid", insertable = false, updatable = false)
    private UserEntity creator; // ✅ creator_uid 로 연결된 UserEntity

    int good;

    String content;

    @Column(name = "is_notice")
    boolean isNotice;

    @Column(name = "modified_uid")
    String modifiedUID;

    @Column(name = "last_modified_at")
    LocalDateTime lastModifiedAt;

    @Override
    public String getEntityType() {
        return EntitiyConstant.BOARD_ENTITY.getValue();
    }
}
