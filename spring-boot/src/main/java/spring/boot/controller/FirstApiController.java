package spring.boot.controller;

import org.springframework.web.bind.annotation.*;
import spring.boot.model.Video;
import spring.boot.service.VideoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FirstApiController {

    private final VideoService videoService;

    public FirstApiController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public List<Video> all() { return videoService.getVideos(); }

    @PostMapping("/videos")
    public Video newVideo(@RequestBody Video video) {
        return videoService.create(video);
    }

}
