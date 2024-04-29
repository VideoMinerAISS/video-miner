package aiss.miner.video.controller;

import aiss.miner.video.model.Caption;
import aiss.miner.video.repository.CaptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/captions")
public class CaptionController {

    @Autowired
    CaptionRepository repository;

    @GetMapping
    public List<Caption> findAll() {
        return repository.findAll();
    }

}
