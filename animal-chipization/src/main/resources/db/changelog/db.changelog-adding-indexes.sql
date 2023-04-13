-- changeSet OlegM:1
CREATE INDEX area_area_points_spgist_index ON area USING spgist (area_points);