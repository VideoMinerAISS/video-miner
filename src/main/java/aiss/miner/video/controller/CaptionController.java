package aiss.miner.video.controller;

import aiss.miner.video.model.Caption;
import aiss.miner.video.repository.CaptionRepository;
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

@Tag(name="Caption",description="Caption management API.")
@RestController
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    // GET /videominer/captions
    @Operation(
            summary="Retrieve all captions",
            description="Get all existing Caption objects.",
            tags={"caption","get"}
    )
    @ApiResponses({ @ApiResponse(
            responseCode="200",
            content={@Content(
                    array = @ArraySchema(schema = @Schema(implementation = Caption.class)),
                    mediaType = "application/json"
            )}
    )})
    @GetMapping
    public List<Caption> findAll() {
        return repository.findAll();
    }

    // GET /videominer/captions/:id
    @Operation(
            summary="Retrieve caption by id",
            description="Get a Caption object by specifying its id.",
            tags={"caption","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Caption.class),
                            mediaType = "application/json"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public Caption findById(@Parameter(description = "id of caption to be searched") @PathVariable String id) {
        Optional<Caption> caption =  repository.findById(id);
        if (caption.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("caption with id %s not found", id));
        }

        return caption.get();
    }
}
