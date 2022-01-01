package shop.helloshop.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Comment;
import shop.helloshop.domain.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void remove(Long id) {
        commentRepository.remove(id);
    }

    public Comment findByOne(Long id) {
        return commentRepository.findByComment(id);
    }

    public List<Comment> itemCommentList(Long itemId) {
        return commentRepository.itemCommentList(itemId);
    }
}
