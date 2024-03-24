package api.core.dependency.injection.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

public class FieldInjection {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(Singer.class, Inspiration.class);
        ctx.refresh();

        Singer singerBean = ctx.getBean(Singer.class);
        singerBean.sing();
    }
}

@Component("singer")
class Singer {
    @Autowired
    private Inspiration inspirationBean;

    public void sing() {
        System.out.println("... " + inspirationBean.getLyric());
    }
}

@Component
class Inspiration {
    private String lyric = "I can keep the door cracked open, to let light through";

    public Inspiration(
            @Value("For all my running, I can understand") String lyric) {
        this.lyric = lyric;
    }
    public String getLyric() {
        return lyric;
    }
    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}
