"use strict";
const mysql = require("mysql");
//local mysql db connection
const dbConn = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "gestion_etudiants",
});
dbConn.connect(function (err) {
  if (err) throw err;
  console.log("Connexion base de donnees avec Succes!");
});
module.exports = dbConn;
