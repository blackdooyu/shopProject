package shop.helloshop.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.helloshop.domain.entity.Comment;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public Comment findByComment(Long id) {
      return em.find(Comment.class,id);
    }

    public void remove(Long id) {
        Comment findComment = findByComment(id);
        em.remove(findComment);
    }

    public List<Comment> itemCommentList(Long id) {
        return em.createQuery("select c from Comment c where c.item.id =:id",Comment.class)
                .setParameter("id",id)
                .getResultList();
    }



}
