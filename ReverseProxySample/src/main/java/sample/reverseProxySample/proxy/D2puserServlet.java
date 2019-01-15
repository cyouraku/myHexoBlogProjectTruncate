package sample.reverseProxySample.proxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

public class D2puserServlet extends ProxyServlet {

	public D2puserServlet() {}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}
}
