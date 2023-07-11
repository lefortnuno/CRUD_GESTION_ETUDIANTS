const router = require("express").Router();
const EtudiantsController = require("../controllers/etudiants.controlleur");

router.post("/", EtudiantsController.addEtudiants);
router.post("/recherche/", EtudiantsController.searchEtudiants);

router.get("/", EtudiantsController.getAllEtudiants);
router.get("/mClasse/", EtudiantsController.getAllMoyenne);
router.get("/admis/", EtudiantsController.getNombreAdmis);
router.get("/redoublant/", EtudiantsController.getNombreRedoublant);
router.get("/:id", EtudiantsController.getIdEtudiants);

router.put("/:id", EtudiantsController.updateEtudiants);

router.delete("/:id", EtudiantsController.deleteEtudiants);

module.exports = router;
