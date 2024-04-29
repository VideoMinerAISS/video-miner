package aiss.miner.video.controller;

import aiss.miner.video.model.Channel;
import aiss.miner.video.repository.ChannelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videominer/channels")
public class ChannelController {

    @Autowired
    ChannelRepository repository;

    // GET /videominer/channels
    @GetMapping
    public List<Channel> findAll() {
        return repository.findAll();
    }

    // GET /videominer/channels/:id
    @GetMapping("/{id}")
    public Channel findById(@PathVariable String id) {
        Optional<Channel> channel =  repository.findById(id);
        if (channel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("channel with id %s not found", id));
        }

        return channel.get();
    }

    // POST /videominer/channels
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
