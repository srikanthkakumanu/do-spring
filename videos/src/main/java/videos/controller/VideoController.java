package videos.controller;

import org.springframework.web.bind.annotation.*;
import videos.domain.Video;
import videos.service.VideoService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("/videos")
    public List<Video> all() { return videoService.getVideos(); }

    @PostMapping("/video")
    public Video newVideo(@RequestBody Video video) {
        return videoService.create(video);
    }

}
