-- changeSet OlegM:1
CREATE INDEX area_area_points_spgist_index ON area USING spgist (area_points);

-- changeSet OlegM:2
CREATE INDEX account_id_index ON account (id);

-- changeSet OlegM:3
CREATE INDEX animal_id_index ON animal (id);

-- changeSet OlegM:4
CREATE INDEX animal_type_id_index ON animal_type (id);

-- changeSet OlegM:5
CREATE INDEX animal_visited_location_id_index ON animal_visited_location (id);

-- changeSet OlegM:6
CREATE INDEX location_point_id_index ON location_point (id);