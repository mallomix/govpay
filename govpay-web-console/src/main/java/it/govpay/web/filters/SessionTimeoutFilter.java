package it.govpay.web.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
* SessionTimeoutFilter supporto alla gestione della sessione utente nell'applicazione.
* Si occupa di controllare se l'utente possiede una sessione attiva, 
* e controllare le pagine richieste dall'utente sono accedibili anche senza sessione (es. pagina di login).
* 
* @author Pintori Giuliano (pintori@link.it)
* @author $Author: apoli $
* @version $Rev: 11026 $, $Date: 2015-06-22 13:14:14 +0200(lun, 22 giu 2015) $
*/
public class SessionTimeoutFilter implements Filter {

	private String loginPage = "public/login.html";
	private String loginErrorPage = "public/loginError.html";
	private List<String> excludedPages = null;
	Logger log = LogManager.getLogger();

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.excludedPages = new ArrayList<String>();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			// is session expire control required for this request?
			if (isSessionControlRequiredForThisResource(httpServletRequest)) {
				HttpSession sessione = httpServletRequest.getSession(false);

				// is session invalid?
				if (isSessionInvalid(httpServletRequest)) {					
					String redirPageUrl = httpServletRequest.getContextPath() + "/";
					
					if(sessione!= null)
						sessione.invalidate();
					
					log.debug("La sessione non e' valida, effettuo redirect...");
					
					//se la pagina richiesta e' quella di login allora redirigo direttamente a quella, altrimenti a quella di timeout
					//redirPageUrl += StringUtils.contains(httpServletRequest.getRequestURI(), getLoginPage()) ? getLoginPage() : getTimeoutPage();
				//	redirPageUrl += getRedirPage(httpServletRequest);
//					log.info("session is invalid! redirecting to page : " + redirPageUrl);
					httpServletResponse.sendRedirect(redirPageUrl);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);

	} 

	/**
	 * 
	 * session shouldn't be checked for some pages. For example: for timeout page..
	 * Since we're redirecting to timeout page from this filter,
	 * if we don't disable session control for it, filter will again redirect to it
	 * and this will be result with an infinite loop... 
	 */
	private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
		String requestPath = httpServletRequest.getRequestURI();

		boolean controlRequired = false;
		if(StringUtils.contains(requestPath, this.loginErrorPage) || 
				StringUtils.contains(requestPath, this.loginPage)){
			controlRequired = false;
		}else{
			if(this.excludedPages.size() > 0)
			for (String page : this.excludedPages) {
				if(StringUtils.contains(requestPath, page)){
					controlRequired = false;
					break;
				}
			}
			else
				controlRequired = true;
		}

		return controlRequired;
	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
		&& !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}

//	private String getRedirPage(HttpServletRequest req){
//		String ctx = req.getContextPath();
//		String reqUri = req.getRequestURI();
//		
//		String reqPage = StringUtils.remove(reqUri, ctx);
		
//		String res = "";
//		if("".equals(reqPage) || "/".equals(reqPage) || StringUtils.contains(reqPage, this.loginPage))
//			res = this.loginPage;
//		else
//			res = this.timeoutPage;
//		
//		return res;
//	}

}
