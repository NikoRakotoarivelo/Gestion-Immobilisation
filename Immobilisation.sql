create table immobilisation (
	id serial,
	article varchar(100),
	prixAchat float,
	dateAchat varchar(20),
	typeAmmortissement int,
	duree int,
	detenteur varchar(100),
	primary key (id)
);

create table ammortissement (
	id serial,
	annee int,
	libelle varchar(100),
	anterieur float,
	exercice float,
	cumul float,
	vnc float,
	primary key (id)
);