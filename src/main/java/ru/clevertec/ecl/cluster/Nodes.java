package ru.clevertec.ecl.cluster;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Slf4j
@ConstructorBinding
@ConfigurationProperties("cluster")
@Getter
public class Nodes {

    Map<Long, List<String>> nodes;
    Map<Long, List<String>> activeNode = new HashMap<>();
}
