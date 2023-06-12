package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CommentResponse;
import peaksoft.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    @Query("select new peaksoft.dto.response.CommentResponse(c.id,c.comment,c.createDate)from Comment c")
    List<CommentResponse> findAllComment();

    @Query("select new peaksoft.dto.response.CommentResponse(c.id,c.comment,c.createDate)from Comment c where c.id=:id")
    Optional<CommentResponse> findByCommentId(Long id);
}