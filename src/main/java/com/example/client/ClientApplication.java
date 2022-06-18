package com.example.client;

import com.example.client.model.Department;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;

import java.util.List;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	HttpGraphQlClient httpGraphQlClient(){
		return HttpGraphQlClient.builder().url("http://localhost:8080/graphql").build();
	}

	@Bean
	public CommandLineRunner CommandLineRunnerBean(HttpGraphQlClient httpGraphQlClient) {
		return (args) -> {
			String query = """
						query {
					       departments {
					         id
					         name
					         employees {
					           id
					           name
					         }
					       }
					     }
					""";

			List<Department> departmentList =  httpGraphQlClient.document(query).retrieve("departments").toEntityList(Department.class).block();
			System.out.println("department List " + departmentList);
		};
	}
}
