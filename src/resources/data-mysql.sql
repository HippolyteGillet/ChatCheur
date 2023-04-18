# Dump of table model.model.user
# ------------------------------------------------------------

LOCK TABLES chatcheur.user WRITE;
INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, PERMISSION,
                            LAST_CONNECTION_T, ACCESS, STATE)
VALUES ('gabyformula', 'gabriel', 'henaux', 'gaby.henaux@gmail.com', 'e4feeef0e2f7ae25ab42a8ab7e3dfd1c6e3181b5321580b9d351952358dd1eb0', 'MODERATOR', CURRENT_TIMESTAMP,
        'BANNED', 'OFFLINE'),
       ('baloo', 'hippolyte', 'gillet', 'hippo.gillet@gmail.com', '0600a614e3541ebfbbdee287d5269a7615a3dd1cf17887bad36b4e6223fd1fa0', 'ADMINISTRATOR', CURRENT_TIMESTAMP,
        'ACCEPTED', 'OFFLINE'),
       ('chofo', 'nathan', 'outrey', 'nathoudu66@gmail.com', '0055e0a0380c5ea7a739d505decc453131c5b308f2a1936110b40a6cd828eb5b', 'USER', CURRENT_TIMESTAMP, 'ACCEPTED',
        'OFFLINE'),
       ('jujulafondue', 'juliette', 'lafond', 'juliettelafond@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'ADMINISTRATOR', CURRENT_TIMESTAMP,
        'ACCEPTED', 'OFFLINE'),
       ('stanounette', 'stanislas', 'bourseau', 'stan_bourseau@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'MODERATOR',
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