// package it.reply.monitoringpillar;
//
// import java.io.IOException;
//
// import javax.ejb.Stateless;
// import javax.servlet.Filter;
// import javax.servlet.FilterChain;
// import javax.servlet.FilterConfig;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.http.HttpServletRequest;
//
// @Stateless
// public class HttpFilter implements Filter {
//
// @Override
// public void doFilter(ServletRequest request, ServletResponse response,
// FilterChain chain)
// throws IOException, ServletException {
//
// /* wrap the request in order to read the inputstream multiple times */
// HttpWrappedRequest multiReadRequest = new
// HttpWrappedRequest((HttpServletRequest) request);
//
// /*
// * here I read the inputstream and do my thing with it; when I pass the
// * wrapped request through the filter chain, the rest of the filters,
// * and request handlers may read the cached inputstream
// */
// doMyThing(multiReadRequest.getInputStream());
// // OR
// getBodyRequest(multiReadRequest.getReader());
// chain.doFilter(multiReadRequest, response);
// }
//
// private String getBodyRequest(HttpWrappedRequest )
//
//
// @Override
// public void init(FilterConfig filterConfig) throws ServletException {
// }
//
// @Override
// public void destroy() {
// }
// }
