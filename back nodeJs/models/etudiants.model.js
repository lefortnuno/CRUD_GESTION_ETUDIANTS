let dbConn = require("../config/db.config");

//#region IDENTATION DE CODE
//#endregion

let Etudiants = function (etudiant) {
  this.numEtud = etudiant.numEtud;
  this.nom = etudiant.nom;
  this.note_math = etudiant.note_math;
  this.note_phys = etudiant.note_phys;
  this.moyenne = etudiant.moyenne;
};

const REQUETE_DE_BASE = `SELECT numEtud, nom, note_math, note_phys, moyenne FROM etudiants `;
const ORDER_BY = ` ORDER BY numEtud DESC `;
const MOYENNE_DE_CLASSE = ` SELECT AVG(moyenne) as mClasse, MIN(moyenne) as mMin, MAX(moyenne) as mMax, COUNT(moyenne)/3 as redoublant, (COUNT(moyenne) - COUNT(moyenne)/3 ) as admis, COUNT(moyenne) as nbEtud FROM etudiants `;
const NOMBRE_ADMIS = ` SELECT COUNT(moyenne) as admis FROM etudiants WHERE moyenne >= 10 `;
const NOMBRE_REDOUBLANTS = ` SELECT COUNT(moyenne) as redoublant FROM etudiants WHERE moyenne < 10 `;

Etudiants.addEtudiants = (newEtudiants, result) => {
  dbConn.query("INSERT INTO Etudiants SET ?", newEtudiants, (err, res) => {
    if (!err) {
      result(null, { success: true, message: "Ajout reussi !" });
    } else {
      result(err, null);
    }
  });
};

Etudiants.getAllEtudiants = (result) => {
  dbConn.query(REQUETE_DE_BASE + ORDER_BY, (err, res) => {
    if (err) {
      result(err, null);
    } else {
      result(null, res);
    }
  });
};

Etudiants.getAllMoyenne = (result) => {
  dbConn.query(MOYENNE_DE_CLASSE, (err, res) => {
    if (err) {
      result(err, null);
    } else {
      result(null, res);
    }
  });
};

Etudiants.getNombreAdmis = (result) => {
  dbConn.query(NOMBRE_ADMIS, (err, res) => {
    if (err) {
      result(err, null);
    } else {
      result(null, res);
    }
  });
};

Etudiants.getNombreRedoublant = (result) => {
  dbConn.query(NOMBRE_REDOUBLANTS, (err, res) => {
    if (err) {
      result(err, null);
    } else {
      result(null, res);
    }
  });
};

Etudiants.getIdEtudiants = (numEtud, result) => {
  dbConn.query(REQUETE_DE_BASE + ` WHERE numEtud = ?`, numEtud, (err, res) => {
    console.log("ID : ");
    console.log(res, err);
    if (err) {
      result(err, null);
    } else {
      if (res.length !== 0) {
        result(null, res);
      } else {
        result(null, res);
      }
    }
  });
};

Etudiants.searchEtudiants = (valeur, result) => {
  dbConn.query(
    REQUETE_DE_BASE + ` WHERE ( nom LIKE '%${valeur.nom}%' )` + ORDER_BY,
    (err, res) => {
      if (err) {
        result({ err, message: "erreur !", success: false }, null);
      } else {
        if (res.length !== 0) {
          result(null, { res, success: true });
        } else {
          result(null, { res, message: "Introuvable !", success: false });
        }
      }
    }
  );
};

Etudiants.updateEtudiants = (newEtudiants, numEtud, result) => {
  dbConn.query(
    `UPDATE etudiants SET ? WHERE numEtud = ${numEtud}`,
    newEtudiants,
    function (err, res) {
      console.log(err, res);
      if (err) {
        result(err, null);
      } else {
        result(null, { success: true, message: "Reussi" });
      }
    }
  );
};

Etudiants.deleteEtudiants = (numEtud, result) => {
  Etudiants.getIdEtudiants(numEtud, (err, resAttribut) => {
    dbConn.query(
      `DELETE FROM etudiants WHERE numEtud = ${numEtud}`,
      function (err, res) {
        if (err) {
          result(err, null);
        } else {
          result(null, { success: true });
        }
      }
    );
  });
};

module.exports = Etudiants;
