package net.vjvj.mybatis;

import net.vjvj.mapper.SampleMapper;
import net.vjvj.mapper.TestMapper;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by fall1999y on 2016. 6. 23..
 */

public class SessionFactoryProvider {
    private static final String mapperFiles[] = {"mybatis/sql/testsql.xml", "mybatis/sql/samplesql.xml"};

    public SqlSessionFactory produceFactory() {
        String resource = "mybatis/mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {

            throw new RuntimeException("Fatal Error.  Cause: " + e, e);
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        for (String mapperFile : mapperFiles) {
            InputStream in = getClass().getClassLoader().getResourceAsStream(mapperFile);
            Configuration configuration = sqlSessionFactory.getConfiguration();
            if (in != null) {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(in, configuration, mapperFile, configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            } else {
                System.err.println("Error file not loaded " + mapperFile);
            }
        }
        return sqlSessionFactory;
    }

    public static void main(String[] args) {
        SessionFactoryProvider provider = new SessionFactoryProvider();
        SqlSessionFactory factory = provider.produceFactory();
        SqlSession s = factory.openSession();

        TestMapper mapper = s.getMapper(TestMapper.class);
        System.out.println(mapper.selectTestTable());
        SampleMapper sampleMapper = s.getMapper(SampleMapper.class);
        System.out.println(sampleMapper.selectSampleTable());
    }
}
