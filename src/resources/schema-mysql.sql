# Dump of table model.model.user
# ------------------------------------------------------------

DROP TABLE IF EXISTS chatcheur.user;
CREATE TABLE chatcheur.user
(
    ID                int NOT NULL AUTO_INCREMENT,
    USER_NAME         varchar(255) DEFAULT NULL,
    FIRST_NAME        varchar(255) DEFAULT NULL,
    LAST_NAME         varchar(255) DEFAULT NULL,
    EMAIL             varchar(255) DEFAULT NULL,
    PASSWORD          varchar(255) DEFAULT NULL,
    PERMISSION        enum ('ADMIN','MODERATOR','NORMAL'),
    LAST_CONNECTION_T timestamp    DEFAULT CURRENT_TIMESTAMP,
    ACCESS            enum ('ACCEPTED','BANNED'),
    STATE             enum ('ONLINE','OFFLINE','AWAY'),
    PRIMARY KEY (ID)
);
ALTER TABLE chatcheur.user MODIFY ACCESS VARCHAR(50);
ALTER TABLE chatcheur.user MODIFY PERMISSION VARCHAR(50);

# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS chatcheur.message;
CREATE TABLE chatcheur.message
(
    ID        int NOT NULL AUTO_INCREMENT,
    USER_ID   varchar(255) DEFAULT NULL,
    TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP,
    CONTENT   varchar(255) DEFAULT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES chatcheur.user (ID)
);

# Dump of table model.log
# ------------------------------------------------------------

DROP TABLE IF EXISTS chatcheur.log;
CREATE TABLE chatcheur.log
(
    ID        int NOT NULL AUTO_INCREMENT,
    USER_ID   varchar(255) DEFAULT NULL,
    TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP,
    TYPELOG   enum ('connection','disconnection','sent','ban','stateUpdate'),
    PRIMARY KEY (ID),
    FOREIGN KEY (USER_ID) REFERENCES chatcheur.user (ID)
);