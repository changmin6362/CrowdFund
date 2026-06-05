package io.github.crowdfund;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@SpringBootApplication
@EnableScheduling
@EnableJdbcRepositories
@Import(MyBatisJdbcConfiguration.class)
@MapperScan(basePackages = "io.github.crowdfund.domain", annotationClass = Mapper.class)
public class CrowdFundApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrowdFundApplication.class, args);
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        factory.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources("classpath:/mapper/**/*.xml")
        );
        factory.setTypeAliasesPackage("io.github.crowdfund");

        return factory;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
