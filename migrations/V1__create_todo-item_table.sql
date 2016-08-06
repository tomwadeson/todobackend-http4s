create table todo_item (
    id serial primary key,
    title text,
    url text not null,
    completed boolean not null,
    "order" integer
);
