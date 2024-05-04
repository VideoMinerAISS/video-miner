package aiss.miner.video.controller;

import aiss.miner.video.model.Video;
import aiss.miner.video.repository.VideoRepository;
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

@Tag(name="Video",description="Video management API.")
@RestController
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;

    // GET /videominer/videos
    @Operation(
            summary="Retrieve all videos",
            description="Get all existing Video objects.",
            tags={"video","get"}
    )
    @ApiResponses({ @ApiResponse(
            responseCode="200",
            content={@Content(
                    array = @ArraySchema(schema = @Schema(implementation = Video.class)),
                    mediaType = "application/json"
            )}
    )})
    @GetMapping
    public List<Video> findAll() {
        return repository.findAll();
    }

    // GET /videominer/videos/:id
    @Operation(
            summary="Retrieve video by id",
            description="Get a Video object by specifying its id.",
            tags={"video","get"}
    )
    @ApiResponses({
            @ApiResponse(
                responseCode = "200",
                content = {@Content(
                        schema = @Schema(implementation = Video.class),
                        mediaType = "application/json"
                )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public Video findById(@Parameter(description = "id of video to be searched") @PathVariable String id) {
        Optional<Video> video =  repository.findById(id);
        if (video.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("video with id %s not found", id));
        }

        return video.get();
    }
}
