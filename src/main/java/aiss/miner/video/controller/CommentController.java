package aiss.miner.video.controller;

import aiss.miner.video.model.Comment;
import aiss.miner.video.repository.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name="Comment",description="Comment management API.")
@RestController
@RequestMapping("/videominer/comments")
public class CommentController {

    @Autowired
    CommentRepository repository;

    // GET /videominer/comments
    @Operation(
            summary="Retrieve all comments",
            description="Get all existing Comment objects.",
            tags={"comment","get"}
    )
    @ApiResponses({ @ApiResponse(
            responseCode="200",
            content={@Content(
                    array = @ArraySchema(schema = @Schema(implementation = Comment.class)),
                    mediaType = "application/json"
            )}
    )})
    @GetMapping
    public List<Comment> findAll() {
        return repository.findAll();
    }

    // GET /videominer/comments/:id
    @Operation(
            summary="Retrieve comment by id",
            description="Get a Comment object by specifying its id.",
            tags={"comment","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Comment.class),
                            mediaType = "application/json"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public Comment findById(@Parameter(description = "id of comment to be searched") @PathVariable String id) {
        Optional<Comment> comment =  repository.findById(id);
        if (comment.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("comment with id %s not found", id));
        }

        return comment.get();
    }
}
