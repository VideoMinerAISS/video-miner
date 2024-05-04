package aiss.miner.video.controller;

import aiss.miner.video.model.Channel;
import aiss.miner.video.repository.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Tag(name="Channel",description="Channel management API.")
@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    // GET /videominer/channels
    @Operation(
            summary="Retrieve all channels",
            description="Get all existing Channel objects.",
            tags={"channel","get"}
    )
    @ApiResponses({ @ApiResponse(
            responseCode="200",
            content={@Content(
                    array = @ArraySchema(schema = @Schema(implementation = Channel.class)),
                    mediaType = "application/json"
            )}
    )})
    @GetMapping
    public List<Channel> findAll() {
        return repository.findAll();
    }

    // GET /videominer/channels/:id
    @Operation(
            summary="Retrieve channel by id",
            description="Get a Channel object by specifying its id.",
            tags={"channel","get"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = {@Content(
                            schema = @Schema(implementation = Channel.class),
                            mediaType = "application/json"
                    )}
            ),
            @ApiResponse(
                    responseCode = "404",
                    content = {@Content(schema = @Schema())}
            )
    })
    @GetMapping("/{id}")
    public Channel findById(@Parameter(description = "id of channel to be searched") @PathVariable String id) {
        Optional<Channel> channel =  repository.findById(id);
        if (channel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("channel with id %s not found", id));
        }

        return channel.get();
    }

    // POST /videominer/channels
    @Operation(
            summary="Create channel",
            description="Create a Channel object by sending it in the body.",
            tags={"channel","post"}
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    content = {@Content(
                            schema = @Schema(implementation = Channel.class),
                            mediaType = "application/json"
                    )}
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = {@Content(schema = @Schema())}
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Channel updateChannel(@Valid @RequestBody Channel channel, Errors errors) {
        if (errors.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "channel format invalid");
        }
        System.out.println("DEBUG - Channel");
        System.out.println(channel);
        return repository.save(channel);
    }
}
