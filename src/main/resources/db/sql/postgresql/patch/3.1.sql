-- GP-677 (3.1.x_blocco_portale)
ALTER TABLE rpt ADD COLUMN bloccante BOOLEAN NOT NULL DEFAULT TRUE;
