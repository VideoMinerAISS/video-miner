package aiss.miner.video.controller;

import aiss.miner.video.model.Caption;
import aiss.miner.video.repository.CaptionRepository;
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
@RequestMapping("/videominer/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    // GET /videominer/captions
    @GetMapping
    public List<Caption> findAll() {
        return repository.findAll();
    }

    // GET /videominer/captions/:id
    @GetMapping("/{id}")
    public Caption findById(@PathVariable String id) {
        Optional<Caption> caption =  repository.findById(id);
        if (caption.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("caption with id %s not found", id));
        }

        return caption.get();
    }
}