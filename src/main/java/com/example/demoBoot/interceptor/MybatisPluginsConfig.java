package

//@Configuration
public class MybatisPluginsConfig {

    //    @Bean
    MybatisDurationInterceptor nfaMybatisInterceptor() {
        return new NfaMybatisInterceptor();
    }
}
