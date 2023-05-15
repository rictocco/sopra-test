package com.company.customer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Long id;
	private String name;
	private String lastName;
	private String address;
	private String city;
	private String emailAddress;
}
