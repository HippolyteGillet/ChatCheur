# Dump of table model.model.user
# ------------------------------------------------------------

LOCK TABLES chatcheur.user WRITE;
INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION,
                            LAST_CONNECTION_T, ACCESS, STATE)
VALUES ('gabyformula', 'gabriel', 'henaux', 'gaby.henaux@gmail.com', 'mercedes08', 'MODERATOR', CURRENT_TIMESTAMP,
        'BANNED', 'OFFLINE'),
       ('baloo', 'hippolyte', 'gillet', 'hippo.gillet@gmail.com', 'GitHub007', 'ADMINISTRATOR', CURRENT_TIMESTAMP,
        'ACCEPTED', 'OFFLINE'),
       ('chofo', 'nathan', 'outrey', 'nathoudu66@gmail.com', '@perpignan', 'USER', CURRENT_TIMESTAMP, 'ACCEPTED',
        'OFFLINE'),
       ('jujulafondue', 'juliette', 'lafond', 'juliettelafond@gmail.com', '123456789', 'ADMINISTRATOR', CURRENT_TIMESTAMP,
        'ACCEPTED', 'OFFLINE'),
       ('stanounette', 'stanislas', 'bourseau', 'stan_bourseau@gmail.com', 'Socket69', 'MODERATOR',
        CURRENT_TIMESTAMP, 'ACCEPTED', 'OFFLINE');
UNLOCK
TABLES;

# Dump of table message
# ------------------------------------------------------------

LOCK TABLES chatcheur.message WRITE;
INSERT INTO chatcheur.message (USER_ID, TIMESTAMP, CONTENT)
VALUES (2, CURRENT_TIMESTAMP, 'Coucou comment vas-tu ?'),
       (3, CURRENT_TIMESTAMP, 'Au top :)');
UNLOCK TABLES;
# Dump of table model.log
# ------------------------------------------------------------

LOCK TABLES chatcheur.log WRITE;
INSERT INTO chatcheur.log (USER_ID, TIMESTAMP, TYPELOG)
VALUES (5, CURRENT_TIMESTAMP, 'CONNECTION'),
       (1, CURRENT_TIMESTAMP,'DISCONNECTION');
UNLOCK TABLES;