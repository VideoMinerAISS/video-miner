package aiss.miner.video.controller;

import aiss.miner.video.model.Comment;
import aiss.miner.video.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;

    // GET /videominer/comments
    @GetMapping
    public List<Comment> findAll() {
        return repository.findAll();
    }

    // GET /videominer/comments/:id
    @GetMapping("/{id}")
    public Comment findById(@PathVariable String id) {
        Optional<Comment> comment =  repository.findById(id);
        if (comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("comment with id %s not found", id));
        }

        return comment.get();
    }
}
