package com.cglee079.changoos.security;
//package com.cglee079.changoos.security;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import com.cglee079.changoos.model.AdminVo;
//
///*implements AuthenticationSuccessHandler <== Spring Security*/
///*로그인 성공할 경우 */
//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//	@Override
//	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth)
//			throws IOException, ServletException {
//		/* 로그인 성공할 경우, 세션에 어트리뷰트 지정 */
//		HttpSession session = request.getSession(true);
//		AdminVo user = (AdminVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		session.setAttribute("admin", user.getUsername());
//
//		response.sendRedirect(request.getContextPath() + "/main/home");
//	}
//
//}
