/*==============================================================*/
/* DATABASE: BPAP  				   �������ݿ�		            	*/
/*==============================================================*/
CREATE DATABASE BPAP DEFAULT CHARACTER SET UTF8 COLLATE UTF8_BIN;



/*==============================================================*/
/* Table: SYS_USER  				   ϵͳ�û�	            	*/
/*==============================================================*/
CREATE TABLE SYS_USER(
	ID					VARCHAR(32)						NOT NULL,
	USERNAME			VARCHAR(30)						NOT NULL,
	PASSOWRD			VARCHAR(30)						NOT NULL,
	CREATEDATE			DATETIME						NOT NULL,
	UPDATEDATE			DATETIME
)
COMMENT = "ϵͳ�û�";
/*==============================================================*/
/* Table: WECHAT_USER  				   ΢�Ź�ע�û�	            */
/*==============================================================*/
CREATE TABLE WECHAT_USER(
	ID					VARCHAR(32)						NOT NULL,
	WECHATID			VARCHAR(50)						NOT NULL,
	SUBSCRIBE			CHAR(1)							NOT NULL,
	OPENID 				VARCHAR(50)						NOT NULL,
	NICKNAME			VARCHAR(50),
	SEX 				CHAR(1),
	CITY 				VARCHAR(20),
	COUNTRY  			VARCHAR(20),
	PROVINCE  			VARCHAR(20),
	LANGUAGE			VARCHAR(20),
	HEADIMGURL			VARCHAR(250), 
	SAVEPATH			VARCHAR(100),
	SUBSCRIBETIME 		VARCHAR(50), 
	UNIONID 			VARCHAR(50),
	REMARK				VARCHAR(50),
	PRIMARY KEY (ID)
)
COMMENT = "΢�Ź�ע�û�";

/*==============================================================*/
/* Table: WECHAT_GROUP  				   ΢���û�����	        */
/*==============================================================*/
CREATE TABLE WECHAT_GROUP(
	ID					VARCHAR(32)						NOT NULL,
	WECHATID			VARCHAR(50)						NOT NULL,
	GROUPID				INT								NOT NULL,
	GROUPNAME 			VARCHAR(50)						NOT NULL,
	COUNT				INT,	
	PRIMARY KEY (ID)
)
COMMENT = "����";

/*==============================================================*/
/* Table: WECHAT_GROUP  				   ΢���û�����	        */
/*==============================================================*/
CREATE TABLE WECHAT_GROUPUSER(
	ID					VARCHAR(32)						NOT NULL,
	WECHATID			VARCHAR(50)						NOT NULL,
	GROUPID				INT								NOT NULL,
	OPENID  			VARCHAR(50)						NOT NULL,
	PRIMARY KEY (ID)
)
COMMENT = "�����û�";