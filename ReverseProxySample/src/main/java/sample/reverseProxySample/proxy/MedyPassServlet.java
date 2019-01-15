package sample.reverseProxySample.proxy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mitre.dsmiley.httpproxy.ProxyServlet;

public class MedyPassServlet extends ProxyServlet {

	public MedyPassServlet(){}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

}
