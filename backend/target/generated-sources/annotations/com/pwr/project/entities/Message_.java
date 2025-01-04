package com.pwr.project.entities;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Message.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Message_ {

	
	/**
	 * @see com.pwr.project.entities.Message#senderId
	 **/
	public static volatile SingularAttribute<Message, String> senderId;
	
	/**
	 * @see com.pwr.project.entities.Message#senderName
	 **/
	public static volatile SingularAttribute<Message, String> senderName;
	
	/**
	 * @see com.pwr.project.entities.Message#recipientId
	 **/
	public static volatile SingularAttribute<Message, String> recipientId;
	
	/**
	 * @see com.pwr.project.entities.Message#offerId
	 **/
	public static volatile SingularAttribute<Message, Long> offerId;
	
	/**
	 * @see com.pwr.project.entities.Message#id
	 **/
	public static volatile SingularAttribute<Message, Long> id;
	
	/**
	 * @see com.pwr.project.entities.Message#message
	 **/
	public static volatile SingularAttribute<Message, String> message;
	
	/**
	 * @see com.pwr.project.entities.Message
	 **/
	public static volatile EntityType<Message> class_;
	
	/**
	 * @see com.pwr.project.entities.Message#timestamp
	 **/
	public static volatile SingularAttribute<Message, LocalDateTime> timestamp;

	public static final String SENDER_ID = "senderId";
	public static final String SENDER_NAME = "senderName";
	public static final String RECIPIENT_ID = "recipientId";
	public static final String OFFER_ID = "offerId";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String TIMESTAMP = "timestamp";

}

