package th.ac.ku.kps.eng.cpe.soa.controller;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import th.ac.ku.kps.eng.cpe.soa.dao.CustomerDAO;
import th.ac.ku.kps.eng.cpe.soa.model.Customer;
import th.ac.ku.kps.eng.cpe.soa.response.model.CommonResponse;

@Path("/services")
public class CustomerService {
	private static final String JWT_TOKEN_KEY = "cpe_eng_soa4";
	CustomerDAO cusDao = new CustomerDAO();

	@GET
	@Path("/customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getUsers() {
		return cusDao.getAllCustomers();
	}

	private String generateToken(Customer c) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
			Date expirationDate = Date.from(ZonedDateTime.now().plusHours(24).toInstant());
			Date issuedAt = Date.from(ZonedDateTime.now().toInstant());
			return JWT.create()
					// Issue date.
					.withIssuedAt(issuedAt)
					// Expiration date.
					.withExpiresAt(expirationDate)
					// User id - here we can put anything we want, but for the example userId is
					// appropriate.
					.withClaim("username", c.getUsername())
					// Issuer of the token.
					.withIssuer("jwtauth")
					// And the signing algorithm.
					.sign(algorithm);
		} catch (JWTCreationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Customer validateToken(String token) {
		try {
			if (token != null) {
				Algorithm algorithm = Algorithm.HMAC256(JWT_TOKEN_KEY);
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("jwtauth").build(); // Reusable verifier
				// instance
				DecodedJWT jwt = verifier.verify(token);
				// Get the userId from token claim.
				Claim cus = jwt.getClaim("username");
				System.out.println(cus.asString());
				// Find user by token subject(id).
				// c userDao = new UserDao();
				return cusDao.findCustomer(cus.asString());
			}
		} catch (JWTVerificationException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Customer validUser(Customer c) {
		return cusDao.findCus(c);
	}

	@Path("/authenticate")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateCredentials(Customer c)
			throws JsonGenerationException, JsonMappingException, IOException {
		Customer cus = validUser(c);
		CommonResponse<String> responsePojo = new CommonResponse<String>();
		if (cus == null) {
			responsePojo.setMsg("Invalid Username or password");
			responsePojo.setStatus("401");
			return Response.status(401).entity(responsePojo).build();
		}
		responsePojo.setResult(generateToken(cus));
		responsePojo.setStatus("200");
		responsePojo.setMsg("OK");
		return Response.ok().entity(responsePojo).build();
	}

	@Path("/customers/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomerByName(@HeaderParam("token") String token, @PathParam("name") String name)
			throws JsonGenerationException, JsonMappingException, IOException {
		Customer cus = validateToken(token);
		CommonResponse<Customer> responsePojo = new CommonResponse<Customer>();
		if (cus != null) {
			responsePojo.setMsg("ok");
			responsePojo.setStatus("200");
			responsePojo.setResult(cusDao.findCustomer(name));
			return Response.status(200).entity(responsePojo).build();
		} else {
			responsePojo.setMsg("Permission denied");
			responsePojo.setStatus("403");
			return Response.status(403).entity(responsePojo).build();
		}
	}
}
