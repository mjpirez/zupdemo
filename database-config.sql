CREATE TABLE IF NOT EXISTS pending_renewals (
	service_order_id BIGINT NOT NULL,
	status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
	service_order TEXT NOT NULL,
	offer_request TEXT NOT NULL,
	updated_by varchar(100) NOT NULL,
	updated_at TIMESTAMP without TIME ZONE,
	created_at TIMESTAMP without TIME ZONE NOT NULL DEFAULT now(),

	CONSTRAINT pk_service_order_id PRIMARY KEY (service_order_id)
);