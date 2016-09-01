package net.vjvj;

import net.vjvj.mapper.SamplePackageTypeMapper;
import net.vjvj.mapper.TestPackageTypeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 * Created by fall1999y on 2016. 6. 23..
 */
public class MapperTest {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory f = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis/mybatis-config.xml"));
        SqlSession s = f.openSession();

        TestPackageTypeMapper t = s.getMapper(TestPackageTypeMapper.class);
        System.out.println(t.selectTestTable());

        SqlSessionFactory f2 = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis/mybatis-config.xml"), "development2");
        SqlSession s2 = f2.openSession();
        System.out.println(s2.getMapper(SamplePackageTypeMapper.class).selectSampleTable());
    }
}
