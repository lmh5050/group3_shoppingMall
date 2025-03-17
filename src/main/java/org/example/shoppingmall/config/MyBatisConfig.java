package org.example.shoppingmall.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {
    @Bean //빈 등록 합니다.
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean(); //팩토리 메소드 쓰겠다
        sessionFactory.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*-mapper.xml")); // 경로 확인 여기에 있는 ~~~.mapper.xml을 다 참고 할게요

        Resource myBatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"); //마이바티스 설정은 이거 입니다.
        sessionFactory.setConfigLocation(myBatisConfig);

        sessionFactory.setTypeAliasesPackage("org/example/shoppingmall/repository/UserRepository"); //리포지토리는 여기를 참조 할게요.

        return sessionFactory.getObject();
    }
}
