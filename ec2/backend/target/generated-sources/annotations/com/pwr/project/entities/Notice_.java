package com.pwr.project.entities;

import com.pwr.project.entities.datatypes.File;
import com.pwr.project.entities.datatypes.NoticeStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Notice.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Notice_ {

	
	/**
	 * @see com.pwr.project.entities.Notice#sellerNumber
	 **/
	public static volatile SingularAttribute<Notice, String> sellerNumber;
	
	/**
	 * @see com.pwr.project.entities.Notice#createdBy
	 **/
	public static volatile SingularAttribute<Notice, String> createdBy;
	
	/**
	 * @see com.pwr.project.entities.Notice#description
	 **/
	public static volatile SingularAttribute<Notice, String> description;
	
	/**
	 * @see com.pwr.project.entities.Notice#files
	 **/
	public static volatile SetAttribute<Notice, File> files;
	
	/**
	 * @see com.pwr.project.entities.Notice#noticeStatus
	 **/
	public static volatile SingularAttribute<Notice, NoticeStatus> noticeStatus;
	
	/**
	 * @see com.pwr.project.entities.Notice#id
	 **/
	public static volatile SingularAttribute<Notice, Long> id;
	
	/**
	 * @see com.pwr.project.entities.Notice#title
	 **/
	public static volatile SingularAttribute<Notice, String> title;
	
	/**
	 * @see com.pwr.project.entities.Notice
	 **/
	public static volatile EntityType<Notice> class_;
	
	/**
	 * @see com.pwr.project.entities.Notice#tags
	 **/
	public static volatile SetAttribute<Notice, String> tags;

	public static final String SELLER_NUMBER = "sellerNumber";
	public static final String CREATED_BY = "createdBy";
	public static final String DESCRIPTION = "description";
	public static final String FILES = "files";
	public static final String NOTICE_STATUS = "noticeStatus";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String TAGS = "tags";

}

