-- liquibase formatted sql

-- changeset OlegM:1681676479940-1
CREATE TABLE "area" ("id" BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL, "area_points" POLYGON, "name" VARCHAR(255), CONSTRAINT "area_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-2
CREATE TABLE "animal_type" ("id" BIGINT NOT NULL, "type" VARCHAR(255), CONSTRAINT "animal_type_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-3
CREATE TABLE "account" ("id" BIGINT NOT NULL, "email" VARCHAR(255), "first_name" VARCHAR(255), "last_name" VARCHAR(255), "password" VARCHAR(255), "role" VARCHAR(255), CONSTRAINT "account_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-4
CREATE TABLE "location_point" ("id" BIGINT NOT NULL, "latitude" FLOAT8, "longitude" FLOAT8, CONSTRAINT "location_point_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-5
ALTER TABLE "area" ADD CONSTRAINT "uk_226rm1fd8fl8ewh0a7n1k8f94" UNIQUE ("name");

-- changeset OlegM:1681676479940-6
ALTER TABLE "animal_type" ADD CONSTRAINT "uk_1glb7dmed18nrsl81lxhnmb48" UNIQUE ("type");

-- changeset OlegM:1681676479940-7
ALTER TABLE "account" ADD CONSTRAINT "uk_q0uja26qgu1atulenwup9rxyr" UNIQUE ("email");

-- changeset OlegM:1681676479940-8
ALTER TABLE "location_point" ADD CONSTRAINT "ukcucfg4v5jnq70l5fc6jljbvvy" UNIQUE ("latitude", "longitude");

-- changeset OlegM:1681676479940-9
CREATE SEQUENCE  IF NOT EXISTS "account_seq" AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset OlegM:1681676479940-10
CREATE SEQUENCE  IF NOT EXISTS "animal_seq" AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset OlegM:1681676479940-11
CREATE SEQUENCE  IF NOT EXISTS "animal_type_seq" AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset OlegM:1681676479940-12
CREATE SEQUENCE  IF NOT EXISTS "animal_visited_location_seq" AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset OlegM:1681676479940-13
CREATE SEQUENCE  IF NOT EXISTS "location_point_seq" AS bigint START WITH 1 INCREMENT BY 50 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset OlegM:1681676479940-14
CREATE TABLE "animal" ("id" BIGINT NOT NULL, "chipping_date_time" TIMESTAMP WITHOUT TIME ZONE, "death_date_time" TIMESTAMP WITHOUT TIME ZONE, "gender" VARCHAR(255), "height" FLOAT4, "length" FLOAT4, "life_status" VARCHAR(255), "weight" FLOAT4, "chipper_id" BIGINT NOT NULL, "chipping_location_id" BIGINT NOT NULL, CONSTRAINT "animal_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-15
CREATE TABLE "animal_animal_types" ("animal_id" BIGINT NOT NULL, "animal_type_id" BIGINT NOT NULL, CONSTRAINT "animal_animal_types_pkey" PRIMARY KEY ("animal_id", "animal_type_id"));

-- changeset OlegM:1681676479940-16
CREATE TABLE "animal_visited_location" ("id" BIGINT NOT NULL, "date_time_of_visit_location_point" TIMESTAMP WITHOUT TIME ZONE, "animal_id" BIGINT NOT NULL, "location_point_id" BIGINT NOT NULL, CONSTRAINT "animal_visited_location_pkey" PRIMARY KEY ("id"));

-- changeset OlegM:1681676479940-17
ALTER TABLE "animal_visited_location" ADD CONSTRAINT "fk2kimptt82y77fegciagy6dvqo" FOREIGN KEY ("animal_id") REFERENCES "animal" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset OlegM:1681676479940-18
ALTER TABLE "animal_animal_types" ADD CONSTRAINT "fk58n2m6wwlr5n3wn1g3y48ixdv" FOREIGN KEY ("animal_type_id") REFERENCES "animal_type" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset OlegM:1681676479940-19
ALTER TABLE "animal" ADD CONSTRAINT "fk9wcnph6oxn6e1dtdsal345fhl" FOREIGN KEY ("chipper_id") REFERENCES "account" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset OlegM:1681676479940-20
ALTER TABLE "animal_visited_location" ADD CONSTRAINT "fkd9yfuf4y98xak8bwjeo4uaxwh" FOREIGN KEY ("location_point_id") REFERENCES "location_point" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset OlegM:1681676479940-21
ALTER TABLE "animal" ADD CONSTRAINT "fkm1uiqh857r8l8ed5edjdymnjy" FOREIGN KEY ("chipping_location_id") REFERENCES "location_point" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;

-- changeset OlegM:1681676479940-22
ALTER TABLE "animal_animal_types" ADD CONSTRAINT "fkomvtmbwt675d3mmbmbttvuce9" FOREIGN KEY ("animal_id") REFERENCES "animal" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION;
