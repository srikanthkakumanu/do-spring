package videos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import videos.domain.Video;
import videos.service.VideoService;

/**
 * '@Controller' is an MVC controller annotation
 */
@Controller
@RequestMapping("/mvc")
public class VideoMvcController {

    private final VideoService videoService;

    public VideoMvcController(VideoService videoService) { this.videoService = videoService; }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

//    @GetMapping("/react")
//    public String react() {
//        return "react";
//    }

    @PostMapping("/new-video")
    public String newVideo(@ModelAttribute Video video) {
        videoService.create(video);

        // Spring MVC directive that sends the browser an HTTP 302 Found to URL /.
        // A 302 redirect is the standard for a soft redirect.
        // (301 is a permanent redirect, instructing the browser to not try the
        // original path again.)
        return "redirect:/";
    }
}


