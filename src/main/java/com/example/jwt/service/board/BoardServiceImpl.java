package com.example.jwt.service.board;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public void create(BoardVO board) {

        boardRepository.save(BoardEntity.builder()
                        .name(board.getName())
                        .creatorUID(SecurityUtil.getCurrentUser().getUID())
                        .content(board.getContent())
                        .good(0)
                        .isNotice(board.isNotice)
                .build());
    }

    @Override
    public List<BoardVO> getList(BoardSearchRequest request) {

        int page = Optional.ofNullable(request.getPage()).orElse(0);
        int limit = Optional.ofNullable(request.getLimit()).orElse(20);

//        // 일단 프론트에서 처리 ..
//        String search = request.getSearch();

        Pageable pageable = PageRequest.of(page, limit, Sort.by(
                Sort.Order.desc("isNotice"),
                Sort.Order.desc("createdAt")
        ));

        Page<BoardEntity> result = boardRepository.findAll(pageable);

        return result.stream().map(BoardVO::toBoardVO).toList();
    }

    @Override
    public BoardVO get(String uid) {
        BoardEntity boardEntity = boardRepository.findById(uid)
                .orElseThrow(() -> new APIException(APIException.ErrorCode.ENTITY_INVALID));

        return BoardVO.toBoardVO(boardEntity);
    }

    @Override
    public void edit(BoardVO board) {
        BoardEntity boardEntity = boardRepository.findById(board.getUID())
                .orElseThrow(() -> new APIException(APIException.ErrorCode.ENTITY_INVALID));

        boardEntity.setUID(board.getUID());
        boardEntity.setName(board.getName());
        boardEntity.setContent(board.getContent());
        boardEntity.setNotice(board.isNotice);
        boardEntity.setModifiedUID(SecurityUtil.getCurrentUser().getUID());
        boardEntity.setLastModifiedAt(LocalDateTime.now());

        boardRepository.save(boardEntity);
    }

    @Override
    public void delete(String uid) {
        BoardEntity boardEntity = boardRepository.findById(uid)
                .orElseThrow(() -> new APIException(APIException.ErrorCode.ENTITY_INVALID));

        String requestUserUID = SecurityUtil.getCurrentUser().getUID();

        if (!boardEntity.getCreatorUID().equals(requestUserUID)) {
            throw new APIException(APIException.ErrorCode.BOARD_NOT_CREATOR);
        }

        boardRepository.deleteById(uid);
    }
}
