package ru.clevertec.ecl.configuration.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class CheckNodeIntegrity implements ApplicationListener<ContextRefreshedEvent> {

    //private final CheckEntity checkEntity;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }

//    @Lazy
//    private final PeriodicHealsCheck periodicHealsCheck;

//    @Value("${cluster.nodes.node81}")
//    protected List<String> nodes8081;
//
//    @Value("${cluster.nodes.node82}")
//    protected List<String> nodes8082;
//
//    @Value("${cluster.nodes.node83}")
//    protected List<String> nodes8083;
//
//    protected Map<String, List<String>> node;
//
//    @Value("${server.port}")
//    protected String localServerPort;
//
//    private final Nodes nodes;
//
//    private final RestTemplate restTemplate;
//
//    @PostConstruct
//    private void localHostPort() {
//        node = Map.of(
//                "8081", nodes8081,
//                "8082", nodes8082,
//                "8083", nodes8083);
//        log.debug("localHostPort map =" +
//                node.keySet()
//                        .stream()
//                        .map(key -> key + "=" + node.get(key))
//                        .collect(Collectors.joining(", ", "{", "}")));
//    }
//
//    private void port1() {
//        Map<String, List<String>> activeNode = nodes.getActiveNode();
//        List<String> list = new ArrayList<>();
//        node.forEach((key, value1) -> {
//            value1.stream()
//                    .filter(s -> s.equals(localServerPort) || codeStatus(replacePort(s)))
//                    .forEach(list::add);
//            activeNode.put(key, list);
//        });
//    }
//
//    private void port() {
//        Map<String, List<String>> activeNode = nodes.getActiveNode();
//        //List<String> list = new ArrayList<>();
//        for (Map.Entry<String, List<String>> value : node.entrySet()) {
//            activeNode.put(value.getKey(), met(value));
//        }
//    }
//
//    private List<String> met(Map.Entry<String, List<String>> value){
//        List<String> list = new ArrayList<>();
//        for (String s : value.getValue()) {
//            if (s.equals(localServerPort) || codeStatus(replacePort(s))) {
//                list.add(s);
//            }
//        }
//        return list;
//    }
//
//    private boolean codeStatus(String url) {
//        log.debug("codeStatus. url post replace = " + url);
//        int codeStatus = 0;
//        int i = 0;
//        while (codeStatus != 200) {
//            codeStatus = checkQuery(url);
//            i++;
//            if (i == 3) return false;
//            log.debug("contextInitialized. code status = " + codeStatus);
//        }
//        return true;
//    }
//
//    @SneakyThrows
//    private int checkQuery(String url) {
//        ResponseEntity<?> forEntity;
//        try {
//            forEntity = restTemplate.getForEntity(url, String.class);
//        } catch (Exception e) {
//            forEntity = ResponseEntity.badRequest().build();
//            log.debug("checkQuery. repeat ");
//            Thread.sleep(1000);
//        }
//
//        log.debug("contextInitialized. ResponseEntity = " + forEntity.toString());
//        return forEntity.getStatusCodeValue();
//    }
//
//    protected String replacePort(String port) {
//        String replace = "http://localhost:".concat(localServerPort).concat("/api/check").replace(localServerPort, port);
//        log.debug("replacePort. replace port = " + replace);
//        return replace;
//    }


}
