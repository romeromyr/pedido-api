package com.garcia.pedido_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
public class PedidoApiApplication {

	public static void main(String[] args) {
		// Si Render inyecta DATABASE_URL en formato postgresql://..., lo convertimos a JDBC
		String databaseUrl = System.getenv("DATABASE_URL");
		if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
			try {
				URI uri = new URI(databaseUrl);
				String userInfo = uri.getUserInfo();
				String user = (userInfo != null && userInfo.contains(":")) ? userInfo.split(":", 2)[0] : (userInfo != null ? userInfo : "");
				String password = (userInfo != null && userInfo.contains(":")) ? userInfo.split(":", 2)[1] : "";
				String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + (uri.getPort() > 0 ? uri.getPort() : 5432) + uri.getPath();
				if (uri.getQuery() != null) {
					jdbcUrl += "?" + uri.getQuery();
				}
				System.setProperty("spring.datasource.url", jdbcUrl);
				System.setProperty("spring.datasource.username", user);
				System.setProperty("spring.datasource.password", password);
				System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
			} catch (Exception e) {
				// Si falla el parseo, Spring usará las variables por defecto
			}
		}
		SpringApplication.run(PedidoApiApplication.class, args);
	}

}
