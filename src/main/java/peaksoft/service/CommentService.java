package peaksoft.service;

import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    SimpleResponse saveComment(Long productId, CommentRequest request);
    List<CommentResponse> getAllComment();
    CommentResponse getCommentById(Long id);
    SimpleResponse updateCommentById(Long id,CommentRequest commentRequest);
    SimpleResponse deleteById(Long id);
}
