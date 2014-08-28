CREATE TABLE nws_event (
   id VARCHAR(256) NOT NULL,
   type VARCHAR(128),
   severity VARCHAR(128),
   SUMMARY VARCHAR(1024),
   starttime TIMESTAMP,
   endtime TIMESTAMP,
   updated TIMESTAMP,
   PRIMARY KEY (id)
);

CREATE TABLE local_event (
    state_num TINYINT NOT NULL,
    county_num SMALLINT NOT NULL,
    id VARCHAR(256) NOT NULL
);

CREATE INDEX local_event_idx ON local_event (state_num, county_num);
CREATE INDEX nws_event_idx ON nws_event (id);
 
PARTITION TABLE local_event ON COLUMN state_num;

CREATE PROCEDURE FindAlert AS
   SELECT id, updated FROM nws_event
   WHERE id = ?;

CREATE PROCEDURE FROM CLASS LoadAlert;

CREATE PROCEDURE GetAlertsByLocation AS
   SELECT w.id, w.summary, w.type, w.severity,
          w.starttime, w.endtime 
          FROM nws_event as w, local_event as l
          WHERE l.id=w.id and 
                l.state_num=? and l.county_num = ? and
                w.endtime > TO_TIMESTAMP(MILLISECOND,?)
          order by w.endtime;

PARTITION PROCEDURE GetAlertsByLocation ON TABLE local_event COLUMN state_num;



