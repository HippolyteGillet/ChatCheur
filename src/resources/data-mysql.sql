# Dump of table modele.modele.user
# ------------------------------------------------------------

LOCK TABLES chatcheur.modele.modele.user WRITE;
INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION,
                            LAST_CONNECTION_T, ACCESS, STATE)
VALUES ('gabyformula', 'gabriel', 'henaux', 'gaby.henaux@gmail.com', 'mercedes08', 'moderator', CURRENT_TIMESTAMP,
        'banned', 'offline'),
       ('baloo', 'hippolyte', 'gillet', 'hippo.gillet@gmail.com', 'GitHub007', 'admin', CURRENT_TIMESTAMP,
        'accepted', 'offline'),
       ('chofo', 'nathan', 'outrey', 'nathoudu66@gmail.com', '@perpignan', 'normal', CURRENT_TIMESTAMP, 'accepted',
        'offline'),
       ('jujulafondue', 'juliette', 'lafond', 'juliettelafond@gmail.com', '123456789', 'admin', CURRENT_TIMESTAMP,
        'accepted', 'offline'),
       ('stanounette', 'stanislas', 'bourseau', 'stan_bourseau@gmail.com', 'Socket69', 'moderator',
        CURRENT_TIMESTAMP, 'accepted', 'offline');
UNLOCK
TABLES;

# Dump of table message
# ------------------------------------------------------------

LOCK TABLES chatcheur.message WRITE;
INSERT INTO chatcheur.message (USER_ID, TIMESTAMP, CONTENT)
VALUES (2, CURRENT_TIMESTAMP, 'Coucou comment vas-tu ?'),
       (3, CURRENT_TIMESTAMP, 'Au top :)');
UNLOCK TABLES;
# Dump of table modele.log
# ------------------------------------------------------------

LOCK TABLES chatcheur.modele.log WRITE;
INSERT INTO chatcheur.log (USER_ID, TIMESTAMP, TYPELOG)
VALUES (5, CURRENT_TIMESTAMP, 'CONNECTION'),
       (1, CURRENT_TIMESTAMP,'DISCONNECTION');
UNLOCK TABLES;