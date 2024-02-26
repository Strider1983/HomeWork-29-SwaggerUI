-- liquibase formatted sql

-- changeset kmezentsev:1
CREATE INDEX student_name_index ON student (name);

-- changeset kmezentsev:2
CREATE INDEX faculty_name_index ON faculty (name);
CREATE INDEX faculty_color_index ON faculty (color);

-- changeset kmezentsev:3
CREATE INDEX faculty_name_color_index ON faculty (name, color);