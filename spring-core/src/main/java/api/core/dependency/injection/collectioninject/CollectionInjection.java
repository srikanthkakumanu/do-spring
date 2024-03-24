package api.core.dependency.injection.collectioninject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static java.lang.System.out;

public class CollectionInjection {

    public static void main(String... args) {

        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(CollectionConfig.class, CollectingBean.class);
        ctx.refresh();

        var collectingBean = ctx.getBean(CollectingBean.class);
        collectingBean.printCollections();
    }
}

@Component
class CollectingBean {

    @Autowired
    @Qualifier("list")
    //@Resource(name = "list")
    //@Value("#{collectionConfig.list}")
    //@Inject @Named("list")
    List<Song> songListResource;

    @Autowired // @Inject
    List<Song> songList;

    @Autowired
    Set<Song> songSet;

    @Autowired @Qualifier("set")
    //@Resource(name = "set")
    Set<Song> songSetResource;

    @Autowired
    Map<String, Song> songMap;

    @Autowired @Qualifier("map")
    //@Resource(name = "map")
    Map<String, Song> songMapResource;

    @Autowired
    Properties props;

    public void printCollections(){
        out.println("-- list injected using @Autowired -- ");
        songList.forEach(s -> out.println(s.getTitle()));

        out.println("-- list injected using @Resource / @Autowired @Qualifier(\"list\") / @Inject @Named(\"list\") -- ");
        songListResource.forEach(s -> out.println(s.getTitle()));

        out.println("-- set injected using @Autowired -- -- ");
        songSet.forEach(s -> out.println(s.getTitle()));

        out.println("-- set injected using @Resource / @Autowired @Qualifier(\"set\") / @Inject @Named(\"set\") -- ");
        songSetResource.forEach(s -> out.println(s.getTitle()));

        out.println("-- map injected using  @Autowired -- ");
        songMap.forEach((k,v) -> out.println(k + ": " + v.getTitle()));

        out.println("-- map injected using @Resource / @Autowired @Qualifier(\"map\") / @Inject @Named(\"map\")-- ");
        songMapResource.forEach((k,v) -> out.println(k + ": " + v.getTitle()));

        out.println("-- props injected with @Autowired -- ");
        props.forEach((k,v) -> out.println(k + ": " + v));
    }
}

@Configuration
class CollectionConfig {

    @Bean
    public List<Song> list () {
        return List.of(
                new Song("Not the end"),
                new Song("Rise Up")
        );
    }

    @Bean
    public Set<Song> set () {
        return Set.of(
                new Song("Ordinary Day"),
                new Song("Birds Fly")
        );
    }

    @Bean
    public Map<String, Song> map () {
        return Map.of(
                "John Mayer", new Song("Gravity"),
                "Ben Barnes", new Song("11:11")
        );
    }

    @Bean
    Properties props () {
        Properties props = new Properties();
        props.put("said.she", "Never Mine");
        props.put("said.he", "Cold and jaded");
        return props;
    }

    @Bean
    public Song song1(){
        return new Song("Here's to hoping");
    }

    @Bean
    public Song song2(){
        return new Song("Wishing the best for you");
    }
}
