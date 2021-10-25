package indi.jl.sp.data.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:/sp-mongo.properties")
public class DataMongoConfiguration {


    @Bean
    MongoCustomConversions mongoCustomConversions() {
        List<ConditionalGenericConverter> conditionalGenericConverters = new ArrayList<>();
        //支持添加自定义mongo类型转换器 implements ConditionalGenericConverter
        // conditionalGenericConverters.add(new AToStringConverter());
        return new MongoCustomConversions(conditionalGenericConverters);
    }

    /**
     * 定义MappingMongoConverter 并去掉对象保存时 一同保存的_class
     *
     * @param factory     mongoDatabase工厂
     * @param context     context
     * @param conversions conversions
     * @return MappingMongoConverter
     */
    @Bean
    MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory, MongoMappingContext context,
                                                MongoCustomConversions conversions) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
        mappingConverter.setCustomConversions(conversions);
        mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return mappingConverter;
    }

    /**
     * 声明swagger分组
     *
     * @return docket
     */
    @Bean
    public Docket dataMongoDocket() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("dataMongo")
                .select()
                .paths(PathSelectors.ant("/dataMongo/**"))
                .build();
    }

}