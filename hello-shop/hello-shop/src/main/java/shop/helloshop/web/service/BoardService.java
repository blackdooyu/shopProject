package shop.helloshop.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Board;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.items.Comment;
import shop.helloshop.web.repository.BoardRepository;
import shop.helloshop.web.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(Long memberId, Board board) {

        Member findMember = memberRepository.findOne(memberId);
        board.setMember(findMember);

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

    //게시물 제목으로 찾기 기능
    public List<Board> boardList(String title) {
        return boardRepository.findTitle(title);
    }


}
