drop database gestion_etudiants;

create database gestion_etudiants;
use gestion_etudiants;

create table ETUDIANTS (
    numEtud int(11) NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    note_math FLOAT(2) NOT NULL,
    note_phys FLOAT(2) NOT NULL,
    moyenne FLOAT(2) NOT NULL,
    PRIMARY KEY (numEtud)
);
 
SELECT AVG(moyenne) as mClasse, MIN(moyenne) as mMin, MAX(moyenne) as mMax, COUNT(moyenne)/3 as admis, (COUNT(moyenne) - COUNT(moyenne)/3 )  as redoublant, COUNT(moyenne) as nbEtud FROM etudiants ;


 AND SELECT COUNT(moyenne) as redoublant FROM etudiants WHERE moyenne < 10 ;
