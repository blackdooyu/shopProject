package shop.helloshop.web.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Board;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public Long save(Board board) {
        em.persist(board);
        return board.getId();
    }

    public Board findOne(Long id) {
        return em.find(Board.class, id);
    }

    public void delete(Board board) {
        em.remove(board);
    }

    public List<Board> findTitle(String title) {

        if (title.trim().isEmpty()){
            return em.createQuery("select b from Board b", Board.class)
                    .getResultList();
        }

        return em.createQuery("select b from Board b where b.title = :title", Board.class)
                .setParameter("title", title)
                .getResultList();
    }


    public void updateBoard(Board board) {
        Board findBoard = em.find(Board.class, board.getId());
        findBoard.updateBoard(board.getTitle(),board.getMainText(),board.getUploadFiles());
    }


}
