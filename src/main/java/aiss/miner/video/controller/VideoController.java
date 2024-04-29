package aiss.miner.video.controller;

import aiss.miner.video.model.Video;
import aiss.miner.video.repository.VideoRepository;
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
@RequestMapping("/videominer/videos")
public class VideoController {

    @Autowired
    VideoRepository repository;

    // GET /videominer/videos
    @GetMapping
    public List<Video> findAll() {
        return repository.findAll();
    }

    // GET /videominer/videos/:id
    @GetMapping("/{id}")
    public Video findById(@PathVariable String id) {
        Optional<Video> video =  repository.findById(id);
        if (video.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("video with id %s not found", id));
        }

        return video.get();
    }
}
