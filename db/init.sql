CREATE SEQUENCE historial_solicitud_seq
    INCREMENT BY 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    START 1
    CACHE 1
    NO CYCLE;
	
CREATE TABLE historial_solicitud(
	id 			INT NOT NULL DEFAULT NEXTVAL('historial_solicitud_seq'),
	timestamp	TIMESTAMP,
	endpoint 	TEXT,
	parametros 	TEXT,
	respuesta 	TEXT,
	error 		TEXT,
	CONSTRAINT pk_historial_solicitud PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_historial_solicitud_timestamp ON historial_solicitud(timestamp);
CREATE INDEX IF NOT EXISTS idx_historial_solicitud_endpoint ON historial_solicitud(endpoint);