package com.pwr.project.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ {

	
	/**
	 * @see com.pwr.project.entities.User#firstName
	 **/
	public static volatile SingularAttribute<User, String> firstName;
	
	/**
	 * @see com.pwr.project.entities.User#cognitoSub
	 **/
	public static volatile SingularAttribute<User, String> cognitoSub;
	
	/**
	 * @see com.pwr.project.entities.User#isSeller
	 **/
	public static volatile SingularAttribute<User, Boolean> isSeller;
	
	/**
	 * @see com.pwr.project.entities.User#surname
	 **/
	public static volatile SingularAttribute<User, String> surname;
	
	/**
	 * @see com.pwr.project.entities.User#id
	 **/
	public static volatile SingularAttribute<User, Long> id;
	
	/**
	 * @see com.pwr.project.entities.User#login
	 **/
	public static volatile SingularAttribute<User, String> login;
	
	/**
	 * @see com.pwr.project.entities.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see com.pwr.project.entities.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;

	public static final String FIRST_NAME = "firstName";
	public static final String COGNITO_SUB = "cognitoSub";
	public static final String IS_SELLER = "isSeller";
	public static final String SURNAME = "surname";
	public static final String ID = "id";
	public static final String LOGIN = "login";
	public static final String EMAIL = "email";

}

