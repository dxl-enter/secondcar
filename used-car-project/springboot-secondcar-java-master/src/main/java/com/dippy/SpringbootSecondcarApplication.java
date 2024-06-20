package com.dippy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.dippy.mapper")
//开启SwaggerUI
@EnableSwagger2
/*SpringSecurity开启角色、权限注解,
    securedEnabled：对应controller中的@Secured("ROLE_角色名")
    prePostEnabled: 对应权限
                    @PreAuthorize 表示访问方法或类在执行之前先判断权
                    @PostAuthorize 表示方法或类执行结束后判断权限
 */
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SpringbootSecondcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecondcarApplication.class, args);
    }

    // 1. 测试切换分支
    // 2. 测试主分支改变后合并到其他分支
    // 3. 测试分支提交、后推送到分支、然后主分支再合并
    // 4. 测试分支提交


    // 5. 分支2

    // 6. 创建分支3

    /**
     * 配置jackson了关于LocalDateTime 的处理
     * 让sysUser实体类中的字段LocalDateTime、LocalDate、LocalTime格式化，
     *  解决退出时出现的问题： Cannot construct instance of java.time.LocalDate (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
     *  接着出现Could not read JSON: Expected array or string.
     *
     *  大题就是让LocalDateTime、LocalDate、LocalTime格式化、然后存入redis中是格式化好的
     *      方法： 1. 可以使用如下bean
     *            2. 也可以使用      // 设置序列化方式
     *                              @JsonDeserialize(using = LocalDateTimeDeserializer.class)
     *                              @JsonSerialize(using = LocalDateTimeSerializer.class)
     *                              // 格式化
     *                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
     *                  这三个注解序列化和格式化关于LocalDateTime等locak...时间
     *
     * @return
     */
    // @Bean(name = "mapperObject")
    // public ObjectMapper getObjectMapper() {
    //     ObjectMapper om = new ObjectMapper();
    //     JavaTimeModule javaTimeModule = new JavaTimeModule();
    //     javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    //     javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    //     javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
    //     om.registerModule(javaTimeModule);
    //     return om;
    // }
}
