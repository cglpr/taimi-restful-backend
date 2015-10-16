package taimi.backend.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * Basic CORS filter. 
 * Notice that this allows all client domains 
 * with same access rights!
 *
 * It would be good practice(=safer) to set the rights at
 *  least for POST, PUT and DELETE domain specific.
 *  
 * --> Seems that Server's own filter overrides this.
 * 
 * @author vpotry
 *
 */
public class CORSFilter implements Filter  {

	Logger logger = Logger.getLogger(CORSFilter.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Initializing configuration...");
		// TODO: Init domain-specific filter configuration here!
		System.out.println("CORSFilter Init");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		System.out.println("CORSFilter doFilter");
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		logger.debug("Dalek: All CORSFilters must be destroyed!");
	}

}
