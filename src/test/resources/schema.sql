DROP TABLE IF EXISTS recipes cascade;
DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS recipes_ingredients;
--- recipes
CREATE TABLE recipes (
                         id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                         title VARCHAR(250),
                         href VARCHAR(250),
                         thumbnail VARCHAR(500)
);

--- logs
CREATE TABLE logs (
                      id INT AUTO_INCREMENT PRIMARY KEY NOT NULL ,
                      timeStamp VARCHAR(500),
                      level VARCHAR(250),
                      thread VARCHAR(500),
                      description CLOB
);

-- recipes_ingredients
create table recipes_ingredients (
                                     recipes_id integer not null,
                                     ingredients varchar(255)
);

alter table recipes_ingredients
    add constraint FK7nlvtcyl7qjws7gh3h6xbruwi
        foreign key (recipes_id)
            references recipes
