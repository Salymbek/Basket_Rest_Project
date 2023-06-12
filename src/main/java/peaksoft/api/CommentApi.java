package peaksoft.api;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.SimpleResponse;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.*;

import peaksoft.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class    CommentApi {

    private final CommentService commentService;

    public CommentApi(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/{productId}")
    public SimpleResponse saveComment(@PathVariable Long productId,
                                      @RequestBody @Valid CommentRequest request){
        return commentService.saveComment(productId, request);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public List<CommentResponse> getAll(){
        return commentService.getAllComment();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public CommentResponse findById (@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse updateComment(@PathVariable Long id, @RequestBody @Valid CommentRequest request){
        return commentService.updateCommentById(id,request);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse deleteComment(@PathVariable Long id){
        return commentService.deleteById(id);
    }


}