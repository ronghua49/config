package com.haohua;    /*
 * @author  Administrator
 * @date 2018/7/19
 */

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.io.IOException;
//标明为配置文件
@Configuration
//自动扫描注解
@ComponentScan
//读取配置文件
@PropertySource("classpath:config.properties")
//开启事务管理
@EnableTransactionManagement
// 自动扫描mapper接口并自动创建实现类对象加入spring容器
@MapperScan(basePackages = "com.haohua.mapper")
public class ApplicationContext {
    @Autowired
    private Environment environment;
    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(environment.getProperty("jdbc.driver"));
        basicDataSource.setUrl(environment.getProperty("jdbc.url"));
        basicDataSource.setUsername(environment.getProperty("jdbc.username"));
        basicDataSource.setPassword(environment.getProperty("jdbc.password"));
        return  basicDataSource;
    }
    @Bean
    //设置事务管理器
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        //设置别名包路径
        sqlSessionFactoryBean.setTypeAliasesPackage("com.haohua.entity");
        //配置mapper.xml所在位置
        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource =  patternResolver.getResources("classpath:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resource);
        //其他配置
        org.apache.ibatis.session.Configuration  configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);
        return  sqlSessionFactoryBean;
    }

}
