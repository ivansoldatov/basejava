create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id          serial not null
        constraint contact_pk primary key,
    resume_uuid char(36)
        constraint contact_resume_uuid_fk references resume on delete cascade,
    type        text,
    value       text   not null
);

alter table contact
    owner to postgres;

create unique index contact_uuid_type_index
    on contact (resume_uuid, type);

create table text_section
(
    id          serial   not null
        constraint text_section_pk
            primary key,
    type        text     not null,
    content     text     not null,
    resume_uuid char(36) not null
        constraint text_section_resume_uuid_fk
            references resume
            on delete cascade
);

alter table text_section
    owner to postgres;

create unique index text_section_uuid_type_index
    on text_section (resume_uuid, type);



