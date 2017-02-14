package org.irp.insurance;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.irp.insurance.repositories.Claims;
import org.irp.insurance.resources.ClaimManagement;
import org.irp.insurance.resources.RiskReport;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class RiskManagementServerApplication extends Application<Configuration> {

	private Claims claims = new Claims();

	public static void main(final String[] args) throws Exception {
		new RiskManagementServerApplication().run(args);
	}

	@Override
	public void run(final Configuration configuration, final Environment environment) {

		environment.jersey().getResourceConfig().packages(getClass().getPackage().getName())
				.register(DeclarativeLinkingFeature.class);

		environment.jersey().register(new RiskReport(claims));
		environment.jersey().register(new ClaimManagement(claims));

		Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
		filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
		filter.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM,
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,Location");
		filter.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
				"Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,Location");
		filter.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");
	}
}
