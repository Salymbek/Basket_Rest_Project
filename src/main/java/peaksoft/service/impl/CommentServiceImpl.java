package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.exception.NotFoundException;
import peaksoft.model.Basket;
import peaksoft.model.Comment;
import peaksoft.model.Product;
import peaksoft.model.User;
import peaksoft.repository.CommentRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.service.CommentService;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ProductRepository productRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ProductRepository productRepository) {
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }


    @Override
    public SimpleResponse saveComment(Long productId, CommentRequest request) {

        Product product = productRepository.findById(productId).orElseThrow(
                ()-> new NotFoundException("Product with id: "+productId+" not found!"));

        Comment comment = new Comment();
        comment.setComment(request.comment());
        comment.setCreateDate(ZonedDateTime.now());

        comment.setProduct(product);
        product.addComments(comment);

        commentRepository.save(comment);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Comment successfully saved!!!")
                .build();
    }

    @Override
    public List<CommentResponse> getAllComment() {
        return commentRepository.findAllComment();
    }

    @Override
    public CommentResponse getCommentById(Long id) {
        return commentRepository.findByCommentId(id).orElseThrow(
                ()-> new NotFoundException("Comment with id: "+id+" not found"));
    }

    @Override
    public SimpleResponse updateCommentById(Long id, CommentRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new NotFoundException("Comment with id: "+id+" not found!")
        );

        comment.setComment(request.comment());

        commentRepository.save(comment);

        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Comment with id: "+id+" successfully updated!!!")
                .build();
    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!commentRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Comment with id - %s is not found!", id))
                    .build();
        }
        commentRepository.deleteById(id);
        return new SimpleResponse(
                HttpStatus.OK,
                String.format("Comment with id - %s is deleted!", id)
        );
    }
}
