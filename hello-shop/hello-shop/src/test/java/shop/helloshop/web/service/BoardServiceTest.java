package shop.helloshop.web.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Board;

import javax.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    BoardService boardService;
    @Autowired
    EntityManager em;

    String title = "테스트 타이들";
    String mainText = "본문 내용 테스트입니다.";

    @Test
    void 게시물_저장() {

        //given
        Board board = new Board();
        board.setTitle(title);
        board.setMainText(mainText);
        //when
        boardService.join(board);

        //then
        assertEquals(title,boardService.findOne(board.getId()).getTitle());
        assertEquals(mainText,boardService.findOne(board.getId()).getMainText());
    }

    @Test
    void 게시물_삭제() {

        //given
        Board board = boardMake();
        //when
        boardService.join(board);
        boardService.remove(board.getId());


        //then
        assertNull(boardService.findOne(board.getId()));
    }

    @Test
    void 게시물_업데이트() {

        //given
        Board board = boardMake();
        //when
        boardService.join(board);
        Board newBoard = new Board();
        String update = "업데이트";
        newBoard.setId(board.getId());
        newBoard.setMainText(update);
        newBoard.setTitle(update);
        boardService.update(newBoard);

        em.flush();
        em.clear();

        //then
        assertEquals(update,boardService.findOne(newBoard.getId()).getTitle());
        assertEquals(update,boardService.findOne(newBoard.getId()).getMainText());
    }

    @Test
    void 게시물_검색() {

        //given
        Board board = boardMake();
        //when
        boardService.join(board);

        em.flush();
        em.clear();

        List<Board> boards = boardService.boardList(title);
        List<Board> testBoards = boardService.boardList("사이즈 0이여야 함");
        //then
        assertEquals(boards.size(),1);
        assertEquals(testBoards.size(),0);
    }


    private Board boardMake() {
        Board board = new Board();
        board.setTitle(title);
        board.setMainText(mainText);
        return board;
    }

}