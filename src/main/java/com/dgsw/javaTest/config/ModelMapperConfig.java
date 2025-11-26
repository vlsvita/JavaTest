package com.dgsw.javaTest.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        
        // 매칭 전략 설정: STRICT (엄격한 매칭)
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true) // null 값은 매핑하지 않음
                .setAmbiguityIgnored(false); // 모호한 매핑은 에러
        
        return modelMapper;
    }
}
