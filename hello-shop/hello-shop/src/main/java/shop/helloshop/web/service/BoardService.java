package shop.helloshop.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Board;
import shop.helloshop.web.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long join(Board board) {
        return boardRepository.save(board);
    }

    @Transactional
    public void remove(Long id) {
        Board findBoard = findOne(id);
        boardRepository.delete(findBoard);
    }

    public Board findOne(Long id) {
        return boardRepository.findOne(id);
    }

    @Transactional
    public void update(Board board) {
        boardRepository.updateBoard(board);
    }


    public List<Board> boardList(String title) {
        return boardRepository.findTitle(title);
    }

}
