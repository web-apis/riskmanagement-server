package org.irp.insurance.representations;

import java.util.List;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.server.Uri;
import org.irp.insurance.resources.ClaimManagement;

public class ClaimsDTO {

	private final int limit, offset, size;

	private final String orderBy;

	private final List<ClaimDTO> claims;

	public ClaimsDTO(int limit, int offset, int size, String orderBy, List<ClaimDTO> claims) {
		super();
		this.limit = limit;
		this.offset = offset;
		this.size = size;
		this.orderBy = orderBy;
		this.claims = claims;
	}

	/**
	 * We use Jersey specific annotations (not part of JAX-RS) to generate the
	 * HATEOAS link relations for pagination automatically.
	 * 
	 * See https://jersey.java.net/documentation/latest/declarative-linking.html
	 * for a more detailed documentation.
	 */
	@InjectLinks({
			@InjectLink(resource = ClaimManagement.class, method = "listClaims", style = Style.ABSOLUTE, bindings = {
					@Binding(name = "offset", value = "${instance.offset}"),
					@Binding(name = "orderBy", value = "${instance.orderBy}"),
					@Binding(name = "limit", value = "${instance.limit}") }, rel = "self"),

			@InjectLink(resource = ClaimManagement.class, method = "listClaims", style = Style.ABSOLUTE, condition = "${instance.offset + instance.limit < instance.size}", bindings = {
					@Binding(name = "offset", value = "${instance.offset + instance.limit}"),
					@Binding(name = "orderBy", value = "${instance.orderBy}"),
					@Binding(name = "limit", value = "${instance.limit}") }, rel = "next"),

			@InjectLink(resource = ClaimManagement.class, method = "listClaims", style = Style.ABSOLUTE, condition = "${instance.offset - instance.limit >= 0}", bindings = {
					@Binding(name = "offset", value = "${instance.offset - instance.limit}"),
					@Binding(name = "orderBy", value = "${instance.orderBy}"),
					@Binding(name = "limit", value = "${instance.limit}") }, rel = "prev") })
	private List<Uri> links;

	public int getLimit() {
		return limit;
	}

	public int getOffset() {
		return offset;
	}

	public int getSize() {
		return size;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public List<ClaimDTO> getClaims() {
		return claims;
	}

	public List<Uri> getLinks() {
		return links;
	}

}
