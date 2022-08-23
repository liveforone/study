# &lt;websecurityconfigureradapter deprecated, transform&gt;
>  spring security 5.7이상 버전에서 더이상 websecurityconfigureradapter를 지원하지 않게 되었고, 이에따라 기존의 코드를 새로운 방법에 맞게 변경해보았다.

## 1. 웹세팅
<pre>
- 기존코드 - 
@Override
public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
}
-------------------------------
- 변경 코드 -
@Bean
public WebSecurityCustomizer webSecurityCustomizer() {
    return (web -> web.ignoring().antMatchers("/css", "/js", "/lib"));
}
WebSecurity -> WebSecurityCustomizer
</pre>

## 2. http 세팅
<pre>
- 기존 코드 -
@Override
protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();
    http
            .authorizeRequests()
            // 페이지 권한 설정
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/board").authenticated()
            .antMatchers("/user/board/post").hasRole("WRITER")
            .antMatchers("/**").permitAll()
            .and() // 로그인 설정
            .formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/user/login/result")
            .permitAll()
            .and() // 로그아웃 설정
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/user/logout/result")
            .invalidateHttpSession(true)
            .and()
            // 403 예외처리 핸들링
                .exceptionHandling().accessDeniedPage("/user/denied");
}
-----------------------------------
- 변경 코드 -
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authenticationProvider(authenticationProvider())  //provider 등록
            .authorizeRequests()
            // 페이지 권한 설정
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/board").authenticated()
            .antMatchers("/user/board/post").hasRole("WRITER")
            .antMatchers("/**").permitAll()
            .and() // 로그인 설정
            .formLogin()
            .loginPage("/user/login")
            .defaultSuccessUrl("/user/login/result")
            .permitAll()
            .and() // 로그아웃 설정
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/user/logout/result")
            .invalidateHttpSession(true)
            .and()
            // 403 예외처리 핸들링
            .exceptionHandling().accessDeniedPage("/user/denied");
    return http.build();
}
HttpSecurity confing -> SecurityFilterChain,
authenticationProvider를 사용해 provider를 주입받는다.
</pre>

## 3. AuthenticationManagerBuilder
<pre>
- 기존 코드 -
@Bean
public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
}
---------------------------------------------------
- 변경 코드 -
@Bean
public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
    auth.setUserDetailsService(userService);
    auth.setPasswordEncoder(passwordEncoder());
    return auth;
}
AuthenticationManagerBuilder -> DaoAuthenticationProvider
이 provider를 필터체인에 주입하여 사용한다.
</pre>
