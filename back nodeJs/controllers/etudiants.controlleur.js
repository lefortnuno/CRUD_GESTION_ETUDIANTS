"use strict";
const Etudiants = require("../models/etudiants.model");

module.exports.addEtudiants = (req, res) => {
  let { nom, note_math, note_phys } = req.body;
  const moyenne = (parseInt(note_math) + parseInt(note_phys)) / 2;

  const newEtudiants = {
    nom,
    note_math,
    note_phys,
    moyenne,
  };

  Etudiants.addEtudiants(newEtudiants, (err, resp) => {
    if (err) {
      res.send(err);
    } else {
      res.send(resp);
    }
  });
};

module.exports.getAllEtudiants = (req, res) => {
  Etudiants.getAllEtudiants((err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.getAllMoyenne = (req, res) => {
  Etudiants.getAllMoyenne((err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.getNombreAdmis = (req, res) => {
  Etudiants.getNombreAdmis((err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.getNombreRedoublant = (req, res) => {
  Etudiants.getNombreRedoublant((err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.getIdEtudiants = (req, res) => {
  Etudiants.getIdEtudiants(req.params.id, (err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.updateEtudiants = (req, res) => {
  const { nom, note_math, note_phys } = req.body;
  const moyenne = (parseInt(note_math) + parseInt(note_phys)) / 2;

  const newEtudiants = { nom, note_math, note_phys, moyenne };

  console.log(" newEtudiants : ", newEtudiants);

  Etudiants.updateEtudiants(newEtudiants, req.params.id, (err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.deleteEtudiants = (req, res) => {
  Etudiants.deleteEtudiants(req.params.id, (err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};

module.exports.searchEtudiants = (req, res) => {
  let { nom } = req.body;
  const valeur = { nom };

  Etudiants.searchEtudiants(valeur, (err, resp) => {
    if (!err) {
      res.send({ data: resp });
    } else {
      res.send({ err });
    }
  });
};
