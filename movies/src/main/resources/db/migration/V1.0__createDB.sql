
create table movie_stats (
    movie_id varchar(255) not null,
    description integer not null check (description>=0),
    director integer not null check (director>=0),
    year integer not null check (year>=0),
    primary key (movie_id));
