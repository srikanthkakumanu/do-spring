package spring.boot.service;

import org.springframework.stereotype.Service;
import spring.boot.model.Video;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    private List<Video> videos = List.of(
            new Video("Need HELP with your SPRING BOOT 3 App?"), //
            new Video("Don't do THIS to your own CODE!"), //
            new Video("SECRETS to fix BROKEN CODE!")
    );

    public List<Video> getVideos () { return this.videos; }

    public Video create (Video video) {
        List<Video> extend = new ArrayList<Video>(videos);
        extend.add(video);
        this.videos = List.copyOf(extend);
        return video;
    }
}
